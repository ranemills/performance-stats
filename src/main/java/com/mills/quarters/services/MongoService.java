package com.mills.quarters.services;

import com.mills.quarters.models.AbstractMongoModel;
import com.mills.quarters.models.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Created by ryan on 08/05/16.
 */
public class MongoService {

    public static <T extends AbstractMongoModel> T setCustomer(T object) {
        if (object.getCustomer() == null) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null) {
                if (principal instanceof AuthUser) {
                    object.setCustomer((AuthUser) principal);
                } else {
                    object.setCustomer(new AuthUser(principal));
                }
            } else {
                object.setCustomer(new AuthUser());
            }
        }
        return object;
    }
}

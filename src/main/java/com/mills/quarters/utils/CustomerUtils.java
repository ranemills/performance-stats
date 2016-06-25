package com.mills.quarters.utils;

import com.mills.quarters.models.AbstractMongoModel;
import com.mills.quarters.models.AuthUser;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * Created by ryan on 25/06/16.
 */
public abstract class CustomerUtils {

    public static Criteria customerCriteria() {
        return new Criteria("customer.$id").is(getCurrentUser().getId());
    }

    public static AuthUser getCurrentUser()
    {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser user;
        if (principal instanceof AuthUser) {
            user = (AuthUser) principal;
        } else {
            user = new AuthUser(principal);
        }
        return user;
    }

    public static <T extends AbstractMongoModel> T setCustomer(T object) {
        object.setCustomer(getCurrentUser());
        return object;
    }
}

package com.mills.quarters.daos;

import com.mills.quarters.models.AuthUser;
import com.mongodb.DBRef;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;

/**
 * Created by ryan on 02/05/16.
 */
abstract class AbstractDao {

    static Criteria customerCriteria() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser user;
        if(principal instanceof AuthUser) {
            user = (AuthUser) principal;
        } else {
            user  = new AuthUser(principal);
        }
        return new Criteria("customer").is(new DBRef("authUser", user.getId()));
    }

}

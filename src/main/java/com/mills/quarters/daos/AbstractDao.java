package com.mills.quarters.daos;

import com.mills.quarters.models.AuthUser;
import com.mongodb.DBRef;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;

import static com.mills.quarters.services.AuthUserService.getCurrentUser;

/**
 * Created by ryan on 02/05/16.
 */
abstract class AbstractDao {

    static Criteria customerCriteria() {
        AuthUser user = getCurrentUser();
        return new Criteria("customer").is(new DBRef("authUser", user.getId()));
    }

}

package com.mills.quarters.services;

import com.mills.quarters.models.AuthUser;
import com.mills.quarters.repositories.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Created by ryan on 21/05/16.
 */
@Service
public class AuthUserService {

    @Autowired
    private AuthUserRepository authUserRepository;

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

    void setCurrentUserAsImported() {
        AuthUser user = authUserRepository.findByUsername(getCurrentUser().getUsername()).get(0);
        user.setHasImported(true);
        authUserRepository.save(user);
    }

}

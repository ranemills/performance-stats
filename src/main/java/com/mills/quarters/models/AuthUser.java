package com.mills.quarters.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ryan on 02/05/16.
 */
public class AuthUser extends User {

    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthUser() {
        super("testAuthUser", "", new ArrayList<GrantedAuthority>());
    }
}

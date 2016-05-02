package com.mills.quarters.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ryan on 02/05/16.
 */
public class AuthUser extends User {

    @Id
    private ObjectId id;

    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthUser() {
        super("testAuthUser", "", new ArrayList<GrantedAuthority>());
    }

    public AuthUser(User principal) {
        super(principal.getUsername(), principal.getPassword(), principal.getAuthorities());
        setId(new ObjectId());
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}

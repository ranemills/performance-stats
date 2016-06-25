package com.mills.performances.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

import static com.mills.performances.MongoConfiguration.DOCUMENT_AUTHUSER;

/**
 * Created by ryan on 02/05/16.
 */
@Document(collection = DOCUMENT_AUTHUSER)
public class AuthUser extends User {

    @Id
    private ObjectId id;
    private boolean hasImported = false;

    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthUser() {
        super("testAuthUser", "", new ArrayList<GrantedAuthority>());
    }

    public AuthUser(User user) {
        super(user.getUsername(), user.getPassword(), user.getAuthorities());
        setId(new ObjectId());
    }

    public boolean hasImported() {
        return hasImported;
    }

    public void setHasImported(boolean hasImported) {
        this.hasImported = hasImported;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}

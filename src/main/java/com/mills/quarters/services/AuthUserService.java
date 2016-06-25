package com.mills.quarters.services;

/**
 * Created by ryan on 25/06/16.
 */
public interface AuthUserService {
    void setCurrentUserAsImported();

    void addUser(String email, String password);
}

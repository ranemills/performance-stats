package com.mills.quarters.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/**
 * Created by ryan on 26/04/16.
 */
@RestController
public class Auth {
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}

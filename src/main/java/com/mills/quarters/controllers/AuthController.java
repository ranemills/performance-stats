package com.mills.quarters.controllers;

import com.mills.quarters.services.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by ryan on 26/04/16.
 */
@RestController
public class AuthController {

    @Autowired
    AuthUserDetailsService authUserDetailsService;

    @RequestMapping("/user")
    public Map<String, Object> user(Principal user) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", user.getName());
        map.put("roles",
                AuthorityUtils.authorityListToSet(((Authentication) user).getAuthorities()));
        return map;
    }

    @RequestMapping("/adduser")
    public void addUser() {
        authUserDetailsService.addUser("ranemills@googlemail.com");
        authUserDetailsService.addUser("ranemills2@googlemail.com");
    }

}

package com.mills.quarters.controllers;

import com.mills.quarters.daos.AuthUserDao;
import com.mills.quarters.models.AuthUser;
import com.mills.quarters.repositories.AuthUserRepository;
import com.mills.quarters.services.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
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

    @Autowired
    AuthUserRepository authUserRepository;

    @RequestMapping("/user")
    public Map<String, Object> user(Principal user) {
        AuthUser authUser = authUserRepository.findByUsername(user.getName()).get(0);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", authUser.getUsername());
        map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) user).getAuthorities()));
        map.put("hasImported", authUser.hasImported());
        return map;
    }

    @RequestMapping("/adduser")
    public void addUser() {
        authUserDetailsService.addUser("ranemills@googlemail.com");
        authUserDetailsService.addUser("ranemills2@googlemail.com");
    }

}

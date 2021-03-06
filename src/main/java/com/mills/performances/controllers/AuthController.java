package com.mills.performances.controllers;

import com.mills.performances.models.AuthUser;
import com.mills.performances.repositories.AuthUserRepository;
import com.mills.performances.services.AuthUserService;
import com.mills.performances.utils.CustomerUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by ryan on 26/04/16.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthUserService _authUserDetailsService;

    @RequestMapping("/user")
    public Map<String, Object> user() {
        AuthUser authUser = CustomerUtils.getCurrentUser();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", authUser.getUsername());
        map.put("hasImported", authUser.hasImported());
        return map;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody Map<String, String> newUser)
    {
        try {
            _authUserDetailsService.addUser(newUser.get("username"), newUser.get("password"));
            return ResponseEntity.ok().body(null);
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

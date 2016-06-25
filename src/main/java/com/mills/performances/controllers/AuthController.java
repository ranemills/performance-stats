package com.mills.performances.controllers;

import com.mills.performances.models.AuthUser;
import com.mills.performances.repositories.AuthUserRepository;
import com.mills.performances.services.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Autowired
    private AuthUserRepository _authUserRepository;

    @RequestMapping("/user")
    public Map<String, Object> user(Principal user) {
        AuthUser authUser = _authUserRepository.findByUsername(user.getName()).get(0);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", authUser.getUsername());
        map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) user).getAuthorities()));
        map.put("hasImported", authUser.hasImported());
        return map;
    }

    @RequestMapping("/register")
    public void registerUser(@RequestParam("username") String username,
                             @RequestParam("password") String password)
        throws Exception
    {
        _authUserDetailsService.addUser(username, password);
    }

}

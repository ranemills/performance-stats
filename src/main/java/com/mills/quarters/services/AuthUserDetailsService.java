package com.mills.quarters.services;

import com.google.common.collect.ImmutableList;
import com.mills.quarters.daos.AuthUserDao;
import com.mills.quarters.models.AuthUser;
import com.mills.quarters.repositories.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by ryan on 28/04/16.
 */
@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    AuthUserDao authUserDao;

    @Autowired
    AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException
    {
        return authUserDao.getUserByEmail(email);
    }

    public void addUser(String email)
    {
        Collection<GrantedAuthority> authorities = ImmutableList.<GrantedAuthority>builder().add(new SimpleGrantedAuthority("USER")).build();
        AuthUser user = new AuthUser(email, "password", authorities);
        authUserRepository.save(user);
    }
}

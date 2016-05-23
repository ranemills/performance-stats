package com.mills.quarters.services;

import com.google.common.collect.ImmutableList;
import com.mills.quarters.daos.AuthUserDao;
import com.mills.quarters.models.AuthUser;
import com.mills.quarters.repositories.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by ryan on 28/04/16.
 */
@Service
public class AuthUserDetailsService implements UserDetailsService {

    private static final Collection<GrantedAuthority> defaultAuthorities = ImmutableList.<GrantedAuthority>builder()
                                                                               .add(new SimpleGrantedAuthority("USER"))
                                                                               .build();
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AuthUserDao authUserDao;
    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException
    {
        return authUserDao.getUserByEmail(email);
    }

    public void addUser(String email, String password)
        throws IllegalArgumentException
    {
        try
        {
            loadUserByUsername(email);
            throw new IllegalArgumentException("User already exists");
        }
        catch(UsernameNotFoundException e)
        {
            AuthUser user = new AuthUser(email, passwordEncoder.encode(password), defaultAuthorities);
            authUserRepository.save(user);
        }
    }
}

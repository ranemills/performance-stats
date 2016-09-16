package com.mills.performances.services.impl;

import com.google.common.collect.ImmutableList;
import com.mills.performances.models.AuthUser;
import com.mills.performances.repositories.AuthUserRepository;
import com.mills.performances.services.AuthUserService;
import com.mills.performances.services.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.mills.performances.utils.CustomerUtils.getCurrentUser;

/**
 * Created by ryan on 21/05/16.
 */
@Service
public class AuthUserServiceImpl implements AuthUserService, UserDetailsService {

    private static final Collection<GrantedAuthority> defaultAuthorities = ImmutableList.<GrantedAuthority>builder()
                                                                               .add(new SimpleGrantedAuthority("USER"))
                                                                               .build();
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public void setCurrentUserAsImported() {
        AuthUser user = authUserRepository.findByUsername(getCurrentUser().getUsername()).get(0);
        user.setHasImported(true);
        authUserRepository.save(user);
    }

    @Override
    public void addUser(String email, String password)
        throws IllegalArgumentException
    {
        try {
            loadUserByUsername(email);
            throw new IllegalArgumentException("User already exists");
        } catch (UsernameNotFoundException e) {
            AuthUser user = new AuthUser(email, passwordEncoder.encode(password), defaultAuthorities);
            authUserRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException
    {
        return authUserRepository.getUserByEmail(email);
    }

}

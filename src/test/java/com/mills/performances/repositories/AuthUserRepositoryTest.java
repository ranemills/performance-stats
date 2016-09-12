package com.mills.performances.repositories;

import com.google.common.collect.ImmutableList;
import com.mills.performances.AbstractIntegrationTest;
import com.mills.performances.models.AuthUser;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by ryan on 02/05/16.
 */
public class AuthUserRepositoryTest extends AbstractIntegrationTest {


    @Test
    public void testRetrieveByEmail() {
        String email = "test@foo.com";

        Collection<GrantedAuthority> authorities = ImmutableList.<GrantedAuthority>builder().add(new SimpleGrantedAuthority("USER")).build();
        AuthUser expectedUser = new AuthUser(email, "password", authorities);
        _authUserRepository.save(expectedUser);

        AuthUser foundUser = _authUserRepository.getUserByEmail(email);
        assertThat(foundUser.getUsername(), is(email));
        assertThat(foundUser.getPassword(), is("password"));
    }

}

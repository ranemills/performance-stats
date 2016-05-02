package com.mills.quarters.daos;

import com.google.common.collect.ImmutableList;
import com.mills.quarters.IntegrationTest;
import com.mills.quarters.models.AuthUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by ryan on 02/05/16.
 */
public class AuthUserDaoTest extends IntegrationTest {


    @Test
    public void testRetrieveByEmail() {
        String email = "test@foo.com";

        Collection<GrantedAuthority> authorities = ImmutableList.<GrantedAuthority>builder().add(new SimpleGrantedAuthority("USER")).build();
        AuthUser expectedUser = new AuthUser(email, "password", authorities);
        authUserRepository.save(expectedUser);

        AuthUser foundUser = authUserDao.getUserByEmail(email);
        assertThat(foundUser.getUsername(), is(email));
        assertThat(foundUser.getPassword(), is("password"));
    }

}

package com.mills.performances.services;

import com.mills.performances.AbstractTest;
import com.mills.performances.models.AuthUser;
import com.mills.performances.repositories.AuthUserRepository;
import com.mills.performances.services.impl.AuthUserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by ryan on 16/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthUserServiceImplTest extends AbstractTest {

    @Mock
    private AuthUserRepository _authUserRepository;
    @InjectMocks
    private AuthUserServiceImpl _authUserService;

    @Test
    public void canSetUserImported() {
        AuthUser authUser = new AuthUser();
        given(_authUserRepository.findByUsername(any(String.class))).willReturn(Collections.singletonList(authUser));

        AuthUser expectedAuthUser = new AuthUser();
        expectedAuthUser.setHasImported(true);

        _authUserService.setCurrentUserAsImported();

        verify(_authUserRepository).save(expectedAuthUser);
    }

    @Test
    public void cannotAddUserThatAlreadyExists() {
        String email = "test@example.com";
        AuthUser authUser = new AuthUser();
        given(_authUserRepository.getUserByEmail(email)).willReturn(authUser);

        try {
            _authUserService.addUser(email, "password");
            fail("was expecting IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "User already exists");
        }
    }

    @Test
    public void canAddUser() {
        String email = "test@example.com";
        String password = "password";
        given(_authUserRepository.getUserByEmail(email)).willThrow(UsernameNotFoundException.class);

        _authUserService.addUser(email, password);

        ArgumentCaptor<AuthUser> captor = ArgumentCaptor.forClass(AuthUser.class);
        verify(_authUserRepository).save(captor.capture());

        assertThat(captor.getValue().getUsername(), is(email));
    }
}
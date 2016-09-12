package com.mills.performances.controllers;

import com.google.common.collect.ImmutableMap;
import com.mills.performances.AbstractIntegrationTest;
import com.mills.performances.models.AuthUser;
import com.mills.performances.services.AuthUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ryan on 10/04/16.
 */
public class AuthControllerTest extends AbstractIntegrationTest {

    @Autowired
    AuthUserService _authUserService;

    @Test
    public void registerCreatesNewAccount()
        throws Exception
    {
        String username = "test_user";
        mockMvc.perform(get("/api/auth/register?username=" + username + "&password=test_password"))
               .andExpect(status().isOk());

        List<AuthUser> imports = _authUserRepository.findByUsername(username);
        assertThat(imports, hasSize(1));
        assertThat(imports.get(0).getUsername(), is(username));
    }

    @Test
    public void cannotRegisterAccountWithSameName()
        throws Exception
    {
        String username = "test_user";
        _authUserService.addUser(username, "password");

        mockMvc.perform(get("/api/auth/register?username=" + username + "&password=test_password"))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void currentUserIsReturned()
        throws Exception
    {
        AuthUser user = _authUserRepository.findAll().get(0);

        Map<String, Object> expectedResult = ImmutableMap.<String, Object>builder().put("name", user.getUsername())
                                                                                   .put("hasImported", false)
                                                                                   .build();

        mockMvc.perform(get("/api/auth/user"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$", equalTo(expectedResult)));
    }


}

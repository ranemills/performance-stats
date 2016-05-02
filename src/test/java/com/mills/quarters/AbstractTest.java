package com.mills.quarters;

import com.mills.quarters.models.AuthUser;
import org.junit.Before;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

/**
 * Created by ryan on 02/05/16.
 */
public abstract class AbstractTest {

    @Before
    public void setUpSecurityContext() {
        Authentication auth = new UsernamePasswordAuthenticationToken(new AuthUser(), "");
        SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }
}

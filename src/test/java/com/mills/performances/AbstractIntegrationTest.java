package com.mills.performances;

import com.mills.performances.models.AuthUser;
import com.mills.performances.repositories.AuthUserRepository;
import com.mills.performances.repositories.BellBoardImportRepository;
import com.mills.performances.repositories.MilestoneFacetRepository;
import com.mills.performances.repositories.MilestoneRepository;
import com.mills.performances.repositories.PerformanceRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

/**
 * Created by ryan on 12/04/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public abstract class AbstractIntegrationTest extends AbstractTest {


    protected final MediaType _contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                           MediaType.APPLICATION_JSON.getSubtype(),
                                                           Charset.forName("utf8"));

    @Autowired
    protected PerformanceRepository _performanceRepository;

    @Autowired
    protected AuthUserRepository _authUserRepository;

    @Autowired
    protected BellBoardImportRepository _bellBoardImportRepository;

    @Autowired
    protected MilestoneFacetRepository _milestoneFacetRepository;

    @Autowired
    protected MilestoneRepository _milestoneRepository;

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext _webApplicationContext;

    @Before
    public void setup()
    {
        _performanceRepository.deleteAll();
        _authUserRepository.deleteAll();
        _bellBoardImportRepository.deleteAll();
        _milestoneFacetRepository.deleteAll();
        _milestoneRepository.deleteAll();

        SecurityContext context = SecurityContextHolder.getContext();
        AuthUser user = _authUserRepository.save(new AuthUser((AuthUser) context.getAuthentication().getPrincipal()));
        Authentication auth = new UsernamePasswordAuthenticationToken(user, "");
        context.setAuthentication(auth);

        mockMvc = MockMvcBuilders.webAppContextSetup(_webApplicationContext)
                                 .build();

    }

}

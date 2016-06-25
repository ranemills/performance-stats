package com.mills.performances;

import com.mills.performances.repositories.AuthUserRepository;
import com.mills.performances.repositories.QuarterRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by ryan on 12/04/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public abstract class IntegrationTest extends AbstractTest {


    protected final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                          MediaType.APPLICATION_JSON.getSubtype(),
                                                          Charset.forName("utf8"));


    @Autowired
    protected QuarterRepository quarterRepository;

    @Autowired
    protected AuthUserRepository authUserRepository;

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup()
    {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        quarterRepository.deleteAll();
        authUserRepository.deleteAll();
    }

}

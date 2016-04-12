package com.mills.quarters.controllers;

import com.mills.quarters.Application;
import com.mills.quarters.models.Quarter;
import com.mills.quarters.repositories.QuarterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by ryan on 10/04/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class QuartersControllerTest {

    @Autowired
    QuarterRepository quarterRepository;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        quarterRepository.deleteAllInBatch();
    }

    @Test
    public void testListAllQuarters() throws Exception {
        quarterRepository.save(new Quarter(5056, "Cambridge", "Major"));
        quarterRepository.save(new Quarter(1296, "Cambridge", "Minor"));
        mockMvc.perform(get("/quarters/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].changes", equalTo(5056)))
                .andExpect(jsonPath("$[0].method", equalTo("Cambridge")))
                .andExpect(jsonPath("$[0].stage", equalTo("Major")));
    }
}

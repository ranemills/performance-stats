package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableList;
import com.mills.quarters.IntegrationTest;
import com.mills.quarters.models.Quarter;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ryan on 10/04/16.
 */
public class QuartersControllerTest extends IntegrationTest {

    @Test
    public void testListAllQuarters() throws Exception {
        quarterRepository.save(ImmutableList.<Quarter>builder()
                                   .add(new Quarter(5056, "Cambridge", "Major"))
                                   .add(new Quarter(1296, "Cambridge", "Minor"))
                                   .build());

        mockMvc.perform(get("/quarters/list"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].changes", equalTo(5056)))
            .andExpect(jsonPath("$[0].method", equalTo("Cambridge")))
            .andExpect(jsonPath("$[0].stage", equalTo("Major")));
    }
}

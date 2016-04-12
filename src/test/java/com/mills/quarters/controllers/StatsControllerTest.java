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
 * Created by ryan on 12/04/16.
 */
public class StatsControllerTest extends IntegrationTest {

    @Test
    public void testGetMethod() throws Exception {
        quarterRepository.save(ImmutableList.<Quarter>builder()
                                   .add(new Quarter(5056, "Cambridge", "Major"))
                                   .add(new Quarter(1296, "Cambridge", "Minor"))
                                   .build());

        mockMvc.perform(get("/stats/methods"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].name", equalTo("Cambridge")))
            .andExpect(jsonPath("$[0].count", equalTo(2)))
            .andExpect(jsonPath("$[0].name", equalTo("Yorkshire")))
            .andExpect(jsonPath("$[0].count", equalTo(1)));
    }
}

package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableList;
import com.mills.quarters.IntegrationTest;
import com.mills.quarters.models.Quarter;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.mills.quarters.builders.QuarterBuilder.quarterBuilder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ryan on 10/04/16.
 */
public class QuartersControllerTest extends IntegrationTest {

    //TODO: Test with parameters

    @Test
    public void testListAllQuarters() throws Exception {
        quarterRepository.save(ImmutableList.<Quarter>builder()
                                   .add(quarterBuilder().changes(5056).method("Cambridge").stage("Major").build())
                                   .add(quarterBuilder().changes(1296).method("Cambridge").stage("Minor").build())
                                   .build());

        mockMvc.perform(get("/api/quarters/list"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].changes", equalTo(5056)))
            .andExpect(jsonPath("$[0].method", equalTo("Cambridge")))
            .andExpect(jsonPath("$[0].stage", equalTo("Major")));
    }
}

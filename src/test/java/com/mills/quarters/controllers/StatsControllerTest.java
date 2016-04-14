package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableList;
import com.mills.quarters.IntegrationTest;
import com.mills.quarters.models.Quarter;
import org.junit.Before;
import org.junit.Test;

import static com.mills.quarters.builders.QuarterBuilder.quarterBuilder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ryan on 12/04/16.
 */
public class StatsControllerTest extends IntegrationTest {

    @Before
    public void addTestData() {
        quarterRepository.save(ImmutableList.<Quarter>builder()
                                   .add(quarterBuilder().changes(5056)
                                            .method("Cambridge")
                                            .stage("Major")
                                            .ringer("Ryan Mills", 1)
                                            .ringer("Lydia", 2)
                                            .build())
                                   .add(quarterBuilder().changes(1296)
                                            .method("Cambridge")
                                            .stage("Minor")
                                            .ringer("Ryan Mills", 1)
                                            .ringer("Lydia", 2)
                                            .build())
                                   .add(quarterBuilder()
                                            .ringer("Ryan Mills", 1)
                                            .ringer("Claire", 2)
                                            .changes(1280)
                                            .method("Yorkshire")
                                            .stage("Major")
                                            .build())
                                   .build());
    }

    @Test
    public void testGetMethods() throws Exception {
        mockMvc.perform(get("/stats/methods"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].method", equalTo("Cambridge")))
            .andExpect(jsonPath("$[0].count", equalTo(2)))
            .andExpect(jsonPath("$[1].method", equalTo("Yorkshire")))
            .andExpect(jsonPath("$[1].count", equalTo(1)));
    }

    @Test
    public void testGetMethodsWithStageParameter() throws Exception {
        mockMvc.perform(get("/stats/methods?stage=Major"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].method", equalTo("Yorkshire")))
            .andExpect(jsonPath("$[0].count", equalTo(1)))
            .andExpect(jsonPath("$[1].method", equalTo("Cambridge")))
            .andExpect(jsonPath("$[1].count", equalTo(1)));
    }

    @Test
    public void testGetStages() throws Exception {
        mockMvc.perform(get("/stats/stages"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].stage", equalTo("Major")))
            .andExpect(jsonPath("$[0].count", equalTo(2)))
            .andExpect(jsonPath("$[1].stage", equalTo("Minor")))
            .andExpect(jsonPath("$[1].count", equalTo(1)));
    }

    @Test
    public void testGetRingers() throws Exception {
        mockMvc.perform(get("/stats/ringers"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].ringer", equalTo("Ryan Mills")))
            .andExpect(jsonPath("$[0].count", equalTo(3)))
            .andExpect(jsonPath("$[1].ringer", equalTo("Lydia")))
            .andExpect(jsonPath("$[1].count", equalTo(2)))
            .andExpect(jsonPath("$[2].ringer", equalTo("Claire")))
            .andExpect(jsonPath("$[2].count", equalTo(1)));
    }

    @Test
    public void testGetRingersWithStageParameter() throws Exception {
        mockMvc.perform(get("/stats/ringers?stage=Major"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].ringer", equalTo("Ryan Mills")))
            .andExpect(jsonPath("$[0].count", equalTo(2)))
            .andExpect(jsonPath("$[1].ringer", equalTo("Claire")))
            .andExpect(jsonPath("$[1].count", equalTo(1)))
            .andExpect(jsonPath("$[2].ringer", equalTo("Lydia")))
            .andExpect(jsonPath("$[2].count", equalTo(1)));
    }


}

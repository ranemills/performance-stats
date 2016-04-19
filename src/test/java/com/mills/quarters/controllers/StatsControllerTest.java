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
                                            .method("Cambridge Surprise")
                                            .stage("Major")
                                            .ringer("Ryan Mills", 1)
                                            .ringer("Lydia", 2)
                                            .build())
                                   .add(quarterBuilder().changes(1296)
                                            .method("Cambridge Surprise")
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
        mockMvc.perform(get("/api/stats/methods"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$['Yorkshire']", equalTo(1)))
            .andExpect(jsonPath("$['Cambridge Surprise']", equalTo(2)))
            .andExpect(jsonPath("$[*]", hasSize(2)));
    }

    @Test
    public void testGetMethodsWithStageParameter() throws Exception {
        mockMvc.perform(get("/api/stats/methods?stage=Major"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$['Yorkshire']", equalTo(1)))
            .andExpect(jsonPath("$['Cambridge Surprise']", equalTo(1)))
            .andExpect(jsonPath("$[*]", hasSize(2)));
    }

    @Test
    public void testGetStages() throws Exception {
        mockMvc.perform(get("/api/stats/stages"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$['Major']", equalTo(2)))
            .andExpect(jsonPath("$['Minor']", equalTo(1)))
            .andExpect(jsonPath("$[*]", hasSize(2)));
    }

    @Test
    public void testGetStagesWithMethodParameter() throws Exception {
        mockMvc.perform(get("/api/stats/stages?method=Cambridge%20Surprise"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$['Major']", equalTo(1)))
            .andExpect(jsonPath("$['Minor']", equalTo(1)))
            .andExpect(jsonPath("$[*]", hasSize(2)));
    }

    @Test
    public void testGetRingers() throws Exception {
        mockMvc.perform(get("/api/stats/ringers"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$['Ryan Mills']", equalTo(3)))
            .andExpect(jsonPath("$['Lydia']", equalTo(2)))
            .andExpect(jsonPath("$['Claire']", equalTo(1)))
            .andExpect(jsonPath("$[*]", hasSize(3)));
    }

    @Test
    public void testGetRingersWithStageParameter() throws Exception {
        mockMvc.perform(get("/api/stats/ringers?stage=Major"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$['Ryan Mills']", equalTo(2)))
            .andExpect(jsonPath("$['Claire']", equalTo(1)))
            .andExpect(jsonPath("$[*]", hasSize(3)));
    }


}

package com.mills.performances.controllers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mills.performances.IntegrationTest;
import com.mills.performances.models.Performance;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Map;

import static com.mills.performances.builders.PerformanceBuilder.quarterBuilder;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ryan on 12/04/16.
 */
public class StatsControllerTest extends IntegrationTest {

    @Before
    public void addTestData()
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        quarterRepository.save(ImmutableList.<Performance>builder()
                                   .add(quarterBuilder().changes(5056)
                                                        .date(sdf.parse("21-04-2015"))
                                                        .method("Cambridge Surprise")
                                                        .stage("Major")
                                                        .ringer(1, "Ryan Mills")
                                                        .ringer(2, "Lydia")
                                                        .build())
                                   .add(quarterBuilder().changes(1296)
                                                        .date(sdf.parse("21-04-2012"))
                                                        .method("Cambridge Surprise")
                                                        .stage("Minor")
                                                        .ringer(1, "Ryan Mills")
                                                        .ringer(2, "Lydia")
                                                        .build())
                                   .add(quarterBuilder().changes(1280)
                                                        .date(sdf.parse("21-04-2012"))
                                                        .method("Yorkshire Surprise")
                                                        .stage("Major")
                                                        .ringer(1, "Ryan Mills")
                                                        .ringer(2, "Claire")
                                                        .build())
                                   .build());
    }

    @Test
    public void testGetMethods()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/methods"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0]", equalTo(filterMatcher("Cambridge Surprise", 2))))
               .andExpect(jsonPath("$[1]", equalTo(filterMatcher("Yorkshire Surprise", 1))));
    }

    @Test
    public void testGetMethodsWithStageParameter()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/methods?stage=Major"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$", containsInAnyOrder(filterMatcher("Cambridge Surprise", 1),
                                                           filterMatcher("Yorkshire Surprise", 1))));
    }

    @Test
    public void testGetStages()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/stages"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0]", equalTo(filterMatcher("Major", 2))))
               .andExpect(jsonPath("$[1]", equalTo(filterMatcher("Minor", 1))));
    }

    @Test
    public void testGetStagesWithMethodParameter()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/stages?method=Cambridge%20Surprise"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$", containsInAnyOrder(filterMatcher("Minor", 1),
                                                           filterMatcher("Major", 1))));
    }

    @Test
    public void testGetStagesWithRingerParameter()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/stages?ringer=Claire"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0]", equalTo(filterMatcher("Major", 1))));
    }

    @Test
    public void testGetRingers()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/ringers"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$", hasSize(3)))
               .andExpect(jsonPath("$[0]", equalTo(filterMatcher("Ryan Mills", 3))))
               .andExpect(jsonPath("$[1]", equalTo(filterMatcher("Lydia", 2))))
               .andExpect(jsonPath("$[2]", equalTo(filterMatcher("Claire", 1))));
    }

    @Test
    public void testGetRingersWithStageParameter()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/ringers?stage=Major"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$[0]", equalTo(filterMatcher("Ryan Mills", 2))))
               .andExpect(jsonPath("$[1]", anyOf(equalTo(filterMatcher("Lydia", 1)),
                                                 equalTo(filterMatcher("Claire", 1)))))
               .andExpect(jsonPath("$[1]", anyOf(equalTo(filterMatcher("Lydia", 1)),
                                                 equalTo(filterMatcher("Claire", 1)))));
    }


    @Test
    public void testGetAvailableFilters()
        throws Exception
    {
        Map<String, String> expectedFilters = ImmutableMap.<String, String>builder()
                                                  .put("method", "Methods")
                                                  .put("ringer", "Ringers")
                                                  .put("stage", "Stages")
                                                  .put("date", "Dates")
                                                  .build();
        mockMvc.perform(get("/api/stats/available"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$", equalTo(expectedFilters)));
    }

    @Test
    public void testGetFilters()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/filters"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$['method']", hasSize(2)))
               .andExpect(jsonPath("$['method'][0]", equalTo(filterMatcher("Cambridge Surprise", 2))))
               .andExpect(jsonPath("$['method'][1]", equalTo(filterMatcher("Yorkshire Surprise", 1))))
               .andExpect(jsonPath("$['stage']", hasSize(2)))
               .andExpect(jsonPath("$['stage'][0]", equalTo(filterMatcher("Major", 2))))
               .andExpect(jsonPath("$['stage'][1]", equalTo(filterMatcher("Minor", 1))))
               .andExpect(jsonPath("$['ringer']", hasSize(3)))
               .andExpect(jsonPath("$['ringer'][0]", equalTo(filterMatcher("Ryan Mills", 3))))
               .andExpect(jsonPath("$['ringer'][1]", equalTo(filterMatcher("Lydia", 2))))
               .andExpect(jsonPath("$['ringer'][2]", equalTo(filterMatcher("Claire", 1))));
    }

    @Test
    public void testGetFiltersWithStageParameter()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/filters?stage=Major"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$['method']", hasSize(2)))
               .andExpect(jsonPath("$['method'][0]", anyOf(equalTo(filterMatcher("Cambridge Surprise", 1)),
                                                           equalTo(filterMatcher("Yorkshire Surprise", 1)))))
               .andExpect(jsonPath("$['method'][1]", anyOf(equalTo(filterMatcher("Cambridge Surprise", 1)),
                                                           equalTo(filterMatcher("Yorkshire Surprise", 1)))))
               .andExpect(jsonPath("$['stage']", hasSize(1)))
               .andExpect(jsonPath("$['stage'][0]", equalTo(filterMatcher("Major", 2))))
               .andExpect(jsonPath("$['ringer'][0]", equalTo(filterMatcher("Ryan Mills", 2))))
               .andExpect(jsonPath("$['ringer'][1]", anyOf(equalTo(filterMatcher("Lydia", 1)),
                                                           equalTo(filterMatcher("Claire", 1)))))
               .andExpect(jsonPath("$['ringer'][1]", anyOf(equalTo(filterMatcher("Lydia", 1)),
                                                           equalTo(filterMatcher("Claire", 1)))));
    }

    @Test
    public void testGetFiltersWithMethodParameter()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/filters?method=Cambridge%20Surprise"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$['method']", hasSize(1)))
               .andExpect(jsonPath("$['method'][0]", equalTo(filterMatcher("Cambridge Surprise", 2))))
               .andExpect(jsonPath("$['stage']", hasSize(2)))
               .andExpect(jsonPath("$['stage'][0]", anyOf(equalTo(filterMatcher("Major", 1)),
                                                          equalTo(filterMatcher("Minor", 1)))))
               .andExpect(jsonPath("$['stage'][1]", anyOf(equalTo(filterMatcher("Major", 1)),
                                                          equalTo(filterMatcher("Minor", 1)))))
               .andExpect(jsonPath("$['ringer']", hasSize(2)))
               .andExpect(jsonPath("$['ringer'][0]", anyOf(equalTo(filterMatcher("Lydia", 2)),
                                                           equalTo(filterMatcher("Ryan Mills", 2)))))
               .andExpect(jsonPath("$['ringer'][1]", anyOf(equalTo(filterMatcher("Lydia", 2)),
                                                           equalTo(filterMatcher("Ryan Mills", 2)))));
    }

    @Test
    public void testGetFiltersWithRingerParameter()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/filters?ringer=Lydia"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$['method']", hasSize(1)))
               .andExpect(jsonPath("$['method'][0]", equalTo(filterMatcher("Cambridge Surprise", 2))))
               .andExpect(jsonPath("$['stage']", hasSize(2)))
               .andExpect(jsonPath("$['stage'][0]", anyOf(equalTo(filterMatcher("Major", 1)),
                                                          equalTo(filterMatcher("Minor", 1)))))
               .andExpect(jsonPath("$['stage'][1]", anyOf(equalTo(filterMatcher("Major", 1)),
                                                          equalTo(filterMatcher("Minor", 1)))))
               .andExpect(jsonPath("$['ringer']", hasSize(2)))
               .andExpect(jsonPath("$['ringer'][0]", anyOf(equalTo(filterMatcher("Ryan Mills", 2)),
                                                           equalTo(filterMatcher("Lydia", 2)))))
               .andExpect(jsonPath("$['ringer'][1]", anyOf(equalTo(filterMatcher("Ryan Mills", 2)),
                                                           equalTo(filterMatcher("Lydia", 2)))));
    }

    @Test
    public void testGetFiltersWithRingerAndStageParameters()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/filters?ringer=Lydia&stage=Major"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$['method']", hasSize(1)))
               .andExpect(jsonPath("$['method'][0]", equalTo(filterMatcher("Cambridge Surprise", 1))))
               .andExpect(jsonPath("$['stage']", hasSize(1)))
               .andExpect(jsonPath("$['stage'][0]", equalTo(filterMatcher("Major", 1))))
               .andExpect(jsonPath("$['ringer']", hasSize(2)))
               .andExpect(jsonPath("$['ringer'][0]", anyOf(equalTo(filterMatcher("Ryan Mills", 1)),
                                                           equalTo(filterMatcher("Lydia", 1)))))
               .andExpect(jsonPath("$['ringer'][1]", anyOf(equalTo(filterMatcher("Ryan Mills", 1)),
                                                           equalTo(filterMatcher("Lydia", 1)))));
    }

    @Test
    public void testGetFiltersWithDateParameter()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/filters?date=21-04-2012"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(contentType))
               .andExpect(jsonPath("$['method']", hasSize(2)))
               .andExpect(jsonPath("$['stage']", hasSize(2)))
               .andExpect(jsonPath("$['ringer']", hasSize(3)))
               .andExpect(jsonPath("$['date']", hasSize(1)))
               .andExpect(jsonPath("$['date'][0]", equalTo(filterMatcher("21-04-2012", 2))));
    }


    private Map<String, Object> filterMatcher(String property, int count) {
        return ImmutableMap.<String, Object>builder()
                   .put("count", count)
                   .put("property", property)
                   .build();
    }

}

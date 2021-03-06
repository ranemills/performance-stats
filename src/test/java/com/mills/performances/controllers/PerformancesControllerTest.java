package com.mills.performances.controllers;

import com.google.common.collect.ImmutableList;
import com.mills.performances.AbstractIntegrationTest;
import com.mills.performances.models.Performance;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;

import static com.mills.performances.builders.PerformanceBuilder.performanceBuilder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ryan on 10/04/16.
 */
public class PerformancesControllerTest extends AbstractIntegrationTest {


    @Test
    public void testListAllPerformances()
        throws Exception
    {
        _performanceRepository.save(ImmutableList.<Performance>builder()
                                   .add(performanceBuilder().changes(5056).method("Cambridge").stage("Major").build())
                                   .add(performanceBuilder().changes(1296).method("Cambridge").stage("Minor").build())
                                   .build());

        mockMvc.perform(get("/api/performances"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].changes", equalTo(5056)))
               .andExpect(jsonPath("$[0].method", equalTo("Cambridge")))
               .andExpect(jsonPath("$[0].stage", equalTo("Major")))
               .andExpect(jsonPath("$[1].changes", equalTo(1296)))
               .andExpect(jsonPath("$[1].method", equalTo("Cambridge")))
               .andExpect(jsonPath("$[1].stage", equalTo("Minor")));
    }

    @Test
    public void testListPerformancesWithStageParameter()
        throws Exception
    {
        _performanceRepository.save(ImmutableList.<Performance>builder()
                                   .add(performanceBuilder().changes(5056).method("Cambridge").stage("Major").build())
                                   .add(performanceBuilder().changes(1296).method("Cambridge").stage("Minor").build())
                                   .build());

        mockMvc.perform(get("/api/performances?stage=Major"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].changes", equalTo(5056)))
               .andExpect(jsonPath("$[0].method", equalTo("Cambridge")))
               .andExpect(jsonPath("$[0].stage", equalTo("Major")));
    }

    @Test
    public void testListPerformancesWithYearParameter()
        throws Exception
    {
        _performanceRepository.save(ImmutableList.<Performance>builder()
                                   .add(performanceBuilder().changes(5056).method("Cambridge").stage("Major")
                                                            .date(new DateTime(2016, 4, 10, 0, 0).toDate())
                                                            .build())
                                   .add(performanceBuilder().changes(1296).method("Cambridge").stage("Minor")
                                                            .date(new DateTime(2015, 4, 10, 0, 0).toDate())
                                                            .build())
                                   .build());

        mockMvc.perform(get("/api/performances?year=2016"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].changes", equalTo(5056)))
               .andExpect(jsonPath("$[0].method", equalTo("Cambridge")))
               .andExpect(jsonPath("$[0].stage", equalTo("Major")));
    }
}

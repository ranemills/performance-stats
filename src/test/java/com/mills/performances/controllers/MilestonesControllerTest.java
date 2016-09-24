package com.mills.performances.controllers;

import com.mills.performances.AbstractIntegrationTest;
import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.Milestone;
import com.mills.performances.models.MilestoneFacet;
import com.mills.performances.models.Performance;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mills.performances.builders.PerformanceBuilder.tritonDelightPerformance;
import static com.mills.performances.builders.PerformanceBuilder.yorkshireMajorPerformance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ryan on 10/04/16.
 */
public class MilestonesControllerTest extends AbstractIntegrationTest {

    @Test
    public void canCreateMilestoneFacet()
        throws Exception
    {
        _bellBoardImportRepository.save(new BellBoardImport("default"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PerformanceProperty.STAGE.toString(), "Major");

        mockMvc.perform(post("/api/milestones/new")
                            .content(jsonObject.toString())
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$.properties['STAGE']", is("Major")))
               .andExpect(jsonPath("$.count", is(0)));

        List<MilestoneFacet> facets = _milestoneFacetRepository.findAll();
        assertThat(facets, hasSize(1));
    }

    @Test
    public void canGetListOfRecentMilestones()
        throws Exception
    {
        Performance triton = tritonDelightPerformance().date(DateTime.now().minusDays(1).toDate()).stage("Major").build();
        Performance yorkshire =  yorkshireMajorPerformance().date(DateTime.now().minusDays(2).toDate()).stage("Major").build();
        _performanceRepository.save(Arrays.asList(triton, yorkshire));

        Map<PerformanceProperty, Object> properties = new HashMap<>();
        properties.put(PerformanceProperty.STAGE, "Major");

        Milestone milestone1 = new Milestone(1, triton, properties);
        Milestone milestone2 = new Milestone(5, yorkshire, properties);
        _milestoneRepository.save(Arrays.asList(milestone1, milestone2));

        mockMvc.perform(get("/api/milestones"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].milestoneValue", is(1)))
               .andExpect(jsonPath("$[0].properties.STAGE", is("Major")))
               .andExpect(jsonPath("$[0].performance.method", is("Triton Delight")))
               .andExpect(jsonPath("$[1].milestoneValue", is(5)))
               .andExpect(jsonPath("$[0].properties.STAGE", is("Major")))
               .andExpect(jsonPath("$[1].performance.method", is("Yorkshire Surprise")));
    }
}

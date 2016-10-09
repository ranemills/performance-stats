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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ryan on 10/04/16.
 */
public class FacetsControllerTest extends AbstractIntegrationTest {

    @Test
    public void canCreateMilestoneFacet()
        throws Exception
    {
        _bellBoardImportRepository.save(new BellBoardImport("default"));

        JSONObject jsonObject = new JSONObject();
        JSONObject propertiesJsonObject = new JSONObject();
        propertiesJsonObject.put(PerformanceProperty.STAGE.toString(), "Major");

        jsonObject.put("properties", propertiesJsonObject);

        mockMvc.perform(post("/api/facets")
                            .content(jsonObject.toString())
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$.properties['STAGE']", is("Major")))
               .andExpect(jsonPath("$.value", is(0)));

        List<MilestoneFacet> facets = _milestoneFacetRepository.findAll();
        assertThat(facets, hasSize(1));
    }

    @Test
    public void getsProperties()
        throws Exception
    {
        mockMvc.perform(get("/api/facets/properties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(_contentType))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$", containsInAnyOrder("STAGE", "LOCATION", "METHOD")));
    }
}

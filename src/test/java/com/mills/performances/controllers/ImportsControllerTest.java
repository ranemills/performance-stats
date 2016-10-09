package com.mills.performances.controllers;

import com.mills.performances.AbstractIntegrationTest;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.services.BellBoardImportService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ryan on 10/04/16.
 */
public class ImportsControllerTest extends AbstractIntegrationTest {

    private static final String rawUrl = "http://bb.ringingworld.co.uk/search" +
                                         ".php?ringer=ryan+mills&length=q-or-p&bells_type=tower";
    private static final String rawExpectedUrl = "http://bb.ringingworld.co.uk/export" +
                                                 ".php?ringer=ryan+mills&length=q-or-p&bells_type=tower";

    private final String encodedUrl;
    private final String encodedExpectedUrl;

    @Autowired
    BellBoardImportService _bellboardImportService;


    public ImportsControllerTest()
        throws UnsupportedEncodingException
    {
        encodedUrl = URLEncoder.encode(rawUrl, "UTF-8");
        encodedExpectedUrl = URLEncoder.encode(rawExpectedUrl, "UTF-8");
    }

    private static String getJsonRequestBody(String bbUrl, String name)
    {
        JSONObject newImport = new JSONObject();
        newImport.put("bbUrl", bbUrl);
        if (!StringUtils.isEmpty(name)) {
            newImport.put("name", name);
        }
        return newImport.toString();
    }

    @Test
    public void testCreateNewImport()
        throws Exception
    {
        mockMvc.perform(post("/api/imports").content(getJsonRequestBody(encodedUrl, null))
                                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$.name", is("bellboard")))
               .andExpect(jsonPath("$.url", is(encodedExpectedUrl)));

        List<BellBoardImport> imports = _bellBoardImportRepository.findAll();
        assertThat(imports, hasSize(1));
        assertThat(imports.get(0).getUrl(), is(encodedExpectedUrl));
        assertThat(imports.get(0).getName(), is("bellboard"));
    }

    @Test
    public void testCreateNewImportWithName()
        throws Exception
    {
        mockMvc.perform(post("/api/imports").content(getJsonRequestBody(encodedUrl, "custom"))
                                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$.name", is("custom")))
               .andExpect(jsonPath("$.url", is(encodedExpectedUrl)));

        List<BellBoardImport> imports = _bellBoardImportRepository.findAll();
        assertThat(imports, hasSize(1));
        assertThat(imports.get(0).getUrl(), is(encodedExpectedUrl));
        assertThat(imports.get(0).getName(), is("custom"));
    }

    @Test
    public void canRefreshImport()
        throws Exception
    {
        DateTimeUtils.setCurrentMillisFixed(0);

        BellBoardImport bellBoardImport = _bellboardImportService.addImport("import", encodedExpectedUrl);
        bellBoardImport.setLastImport(DateTime.now().minusDays(1).toDate());

        _bellBoardImportRepository.save(bellBoardImport);

        String requestUrl = String.format("/api/imports/%s/update", bellBoardImport.getName());

        mockMvc.perform(post(requestUrl))
               .andExpect(status().isOk());

        BellBoardImport newImport = _bellBoardImportRepository.findByName(bellBoardImport.getName());
        assertThat(newImport.getLastImport(), is(DateTime.now().toDate()));
    }
}

package com.mills.performances.controllers;

import com.mills.performances.AbstractIntegrationTest;
import com.mills.performances.models.BellBoardImport;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ryan on 10/04/16.
 */
public class BellBoardControllerTest extends AbstractIntegrationTest {

    @Test
    public void testCreateNewImport()
        throws Exception
    {
        String rawUrl = "http://bb.ringingworld.co.uk/search.php?ringer=ryan+mills&length=q-or-p&bells_type=tower";
        String rawExpectedUrl = "http://bb.ringingworld.co.uk/export" +
                                ".php?ringer=ryan+mills&length=q-or-p&bells_type=tower";
        String encodedUrl = URLEncoder.encode(rawUrl, "UTF-8");
        String encodedExpectedUrl = URLEncoder.encode(rawExpectedUrl, "UTF-8");
        String requestUrl = String.format("/api/bellboard/import?bbUrl=%s", encodedUrl);
        mockMvc.perform(get(requestUrl))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType));

        List<BellBoardImport> imports = _bellBoardImportRepository.findAll();
        assertThat(imports, hasSize(1));
        assertThat(imports.get(0).getUrl(), is(encodedExpectedUrl));
        assertThat(imports.get(0).getName(), is("bellboard"));
    }

    @Test
    public void testCreateNewImportWithName()
        throws Exception
    {
        String rawUrl = "http://bb.ringingworld.co.uk/search.php?ringer=ryan+mills&length=q-or-p&bells_type=tower";
        String rawExpectedUrl = "http://bb.ringingworld.co.uk/export" +
                                ".php?ringer=ryan+mills&length=q-or-p&bells_type=tower";
        String encodedUrl = URLEncoder.encode(rawUrl, "UTF-8");
        String encodedExpectedUrl = URLEncoder.encode(rawExpectedUrl, "UTF-8");
        String requestUrl = String.format("/api/bellboard/import?name=custom&bbUrl=%s", encodedUrl);
        mockMvc.perform(get(requestUrl))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType));

        List<BellBoardImport> imports = _bellBoardImportRepository.findAll();
        assertThat(imports, hasSize(1));
        assertThat(imports.get(0).getUrl(), is(encodedExpectedUrl));
        assertThat(imports.get(0).getName(), is("custom"));
    }
}

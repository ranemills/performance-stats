package com.mills.performances.controllers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mills.performances.AbstractIntegrationTest;
import com.mills.performances.models.Performance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import static com.mills.performances.builders.PerformanceBuilder.performanceBuilder;
import static org.hamcrest.CoreMatchers.is;
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
public class StatsControllerTest extends AbstractIntegrationTest {

    private static final String CAMBRIDGE = "Cambridge Surprise";
    private static final String YORKSHIRE = "Yorkshire Surprise";
    private static final String MINOR = "Minor";
    private static final String MAJOR = "Major";

    private TimeZone _defaultTimeZone;

    private static final String ALL_SAINTS = "All Saints, Wigston Magna, Leicestershire";
    private static final String ST_WISTAN = "St Wistan, Wigston Magna, Leicestershire";

    @Before
    public void fixTimeZone() {
        _defaultTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

    @Before
    public void addTestData()
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date aprilFifteen = sdf.parse("21-04-2015");
        Date aprilTwelve = sdf.parse("21-04-2012");

        _performanceRepository.save(ImmutableList.<Performance>builder()
                                        .add(performanceBuilder().changes(5056)
                                                                 .date(aprilFifteen)
                                                                 .method(CAMBRIDGE)
                                                                 .stage(MAJOR)
                                                                 .location("All Saints", "Wigston Magna",
                                                                           "Leicestershire")
                                                                 .ringer(1, "Ryan Mills")
                                                                 .ringer(2, "Lydia")
                                                                 .time(3, 15)
                                                                 .build())
                                        .add(performanceBuilder().changes(1296)
                                                                 .date(aprilTwelve)
                                                                 .method(CAMBRIDGE)
                                                                 .stage(MINOR)
                                                                 .location("St Wistan", "Wigston Magna",
                                                                           "Leicestershire")
                                                                 .ringer(1, "Ryan Mills")
                                                                 .ringer(2, "Lydia")
                                                                 .time(45)
                                                                 .build())
                                        .add(performanceBuilder().changes(1280)
                                                                 .date(aprilTwelve)
                                                                 .method(YORKSHIRE)
                                                                 .stage(MAJOR)
                                                                 .location("All Saints", "Wigston Magna",
                                                                           "Leicestershire")
                                                                 .ringer(1, "Ryan Mills")
                                                                 .ringer(2, "Claire")
                                                                 .time(60)
                                                                 .build())
                                        .build());
    }

    @After
    public void resetTimeZone() {
        TimeZone.setDefault(_defaultTimeZone);
    }

    @Test
    public void testGetMethods()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/methods"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
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
               .andExpect(content().contentType(_contentType))
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
               .andExpect(content().contentType(_contentType))
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
               .andExpect(content().contentType(_contentType))
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
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0]", equalTo(filterMatcher("Major", 1))));
    }

    @Test
    public void testGetRingers()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/ringers"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
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
               .andExpect(content().contentType(_contentType))
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
                                                  .put("stage", "Stages")
                                                  .put("ringer", "Ringers")
                                                  .put("date", "Dates")
                                                  .put("year", "Years")
                                                  .put("location", "Locations")
                                                  .build();
        mockMvc.perform(get("/api/stats/available"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$", equalTo(expectedFilters)));
    }

    @Test
    public void testGetFilters()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/filters"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$['method']", hasSize(2)))
               .andExpect(jsonPath("$['method'][0]", equalTo(filterMatcher("Cambridge Surprise", 2))))
               .andExpect(jsonPath("$['method'][1]", equalTo(filterMatcher("Yorkshire Surprise", 1))))
               .andExpect(jsonPath("$['stage']", hasSize(2)))
               .andExpect(jsonPath("$['stage'][0]", equalTo(filterMatcher("Major", 2))))
               .andExpect(jsonPath("$['stage'][1]", equalTo(filterMatcher("Minor", 1))))
               .andExpect(jsonPath("$['ringer']", hasSize(3)))
               .andExpect(jsonPath("$['ringer'][0]", equalTo(filterMatcher("Ryan Mills", 3))))
               .andExpect(jsonPath("$['ringer'][1]", equalTo(filterMatcher("Lydia", 2))))
               .andExpect(jsonPath("$['ringer'][2]", equalTo(filterMatcher("Claire", 1))))
               .andExpect(jsonPath("$['year'][0]", equalTo(filterMatcher(2015, 1))))
               .andExpect(jsonPath("$['year'][1]", equalTo(filterMatcher(2012, 2))))
               .andExpect(jsonPath("$['location']", hasSize(2)))
               .andExpect(jsonPath("$['location'][0]", equalTo(filterMatcher(ALL_SAINTS, 2))))
               .andExpect(jsonPath("$['location'][1]", equalTo(filterMatcher(ST_WISTAN, 1))));
    }

    @Test
    public void testGetFiltersWithStageParameter()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/filters?stage=Major"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
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
                                                           equalTo(filterMatcher("Claire", 1)))))
               .andExpect(jsonPath("$['location']", hasSize(1)))
               .andExpect(jsonPath("$['location'][0]", equalTo(filterMatcher(ALL_SAINTS, 2))));
    }

    @Test
    public void testGetFiltersWithMethodParameter()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/filters?method=Cambridge%20Surprise"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
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
                                                           equalTo(filterMatcher("Ryan Mills", 2)))))
               .andExpect(jsonPath("$['location']", hasSize(2)))
               .andExpect(jsonPath("$['location'][0]", anyOf(equalTo(filterMatcher(ALL_SAINTS, 1)),
                                                             equalTo(filterMatcher(ST_WISTAN, 1)))))
               .andExpect(jsonPath("$['location'][1]", anyOf(equalTo(filterMatcher(ALL_SAINTS, 1)),
                                                             equalTo(filterMatcher(ST_WISTAN, 1)))));
    }

    @Test
    public void testGetFiltersWithRingerParameter()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/filters?ringer=Lydia"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
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
                                                           equalTo(filterMatcher("Lydia", 2)))))
               .andExpect(jsonPath("$['location']", hasSize(2)))
               .andExpect(jsonPath("$['location'][0]", anyOf(equalTo(filterMatcher(ALL_SAINTS, 1)),
                                                             equalTo(filterMatcher(ST_WISTAN, 1)))))
               .andExpect(jsonPath("$['location'][1]", anyOf(equalTo(filterMatcher(ALL_SAINTS, 1)),
                                                             equalTo(filterMatcher(ST_WISTAN, 1)))));
    }

    @Test
    public void testGetFiltersWithRingerAndStageParameters()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/filters?ringer=Lydia&stage=Major"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$['method']", hasSize(1)))
               .andExpect(jsonPath("$['method'][0]", equalTo(filterMatcher("Cambridge Surprise", 1))))
               .andExpect(jsonPath("$['stage']", hasSize(1)))
               .andExpect(jsonPath("$['stage'][0]", equalTo(filterMatcher("Major", 1))))
               .andExpect(jsonPath("$['ringer']", hasSize(2)))
               .andExpect(jsonPath("$['ringer'][0]", anyOf(equalTo(filterMatcher("Ryan Mills", 1)),
                                                           equalTo(filterMatcher("Lydia", 1)))))
               .andExpect(jsonPath("$['ringer'][1]", anyOf(equalTo(filterMatcher("Ryan Mills", 1)),
                                                           equalTo(filterMatcher("Lydia", 1)))))
               .andExpect(jsonPath("$['location']", hasSize(1)))
               .andExpect(jsonPath("$['location'][0]", equalTo(filterMatcher(ALL_SAINTS, 1))));
    }

    @Test
    public void testGetFiltersWithDateParameter()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/filters?date=21-04-2012"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$['date']", hasSize(1)))
               .andExpect(jsonPath("$['date'][0]", equalTo(filterMatcher("21-04-2012", 2))))
               .andExpect(jsonPath("$['method']", hasSize(2)))
               .andExpect(jsonPath("$['stage']", hasSize(2)))
               .andExpect(jsonPath("$['ringer']", hasSize(3)))
               .andExpect(jsonPath("$['location']", hasSize(2)))
               .andExpect(jsonPath("$['location'][0]", anyOf(equalTo(filterMatcher(ALL_SAINTS, 1)),
                                                             equalTo(filterMatcher(ST_WISTAN, 1)))))
               .andExpect(jsonPath("$['location'][1]", anyOf(equalTo(filterMatcher(ALL_SAINTS, 1)),
                                                             equalTo(filterMatcher(ST_WISTAN, 1)))));
    }

    @Test
    public void getSnapshot()
        throws Exception
    {
        mockMvc.perform(get("/api/stats/snapshot"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(_contentType))
               .andExpect(jsonPath("$.total", is(3)))
               .andExpect(jsonPath("$.ringers", is(3)))
               .andExpect(jsonPath("$.methods", is(2)))
               .andExpect(jsonPath("$.towers", is(2)))
               .andExpect(jsonPath("$.time", is(300)));
    }


    private Map<String, Object> filterMatcher(Object property, int count) {
        return ImmutableMap.<String, Object>builder()
                   .put("count", count)
                   .put("property", property)
                   .build();
    }

}

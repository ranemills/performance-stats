package com.mills.performances.services;

import com.mills.bellboard.models.xml.BBPerformance;
import com.mills.performances.AbstractTest;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.Performance;
import com.mills.performances.services.impl.BellBoardServiceImpl;
import com.mills.bellboard.services.BellBoardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.mills.bellboard.models.BBPerformanceBuilder.tritonDelightBbPerformance;
import static com.mills.bellboard.models.BBPerformanceBuilder.yorkshireMajorBbPerformance;
import static com.mills.performances.builders.PerformanceBuilder.tritonDelightPerformance;
import static com.mills.performances.builders.PerformanceBuilder.yorkshireMajorPerformance;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.isNull;

/**
 * Created by ryan on 23/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BellBoardServiceImplTest extends AbstractTest {

    private static final String ID = "101";

    private static final BBPerformance BBPERFORMANCE1 = yorkshireMajorBbPerformance().build();
    private static final BBPerformance BBPERFORMANCE2 = tritonDelightBbPerformance().build();

    private Performance performance1;
    private Performance performance2;

    @Mock
    private BellBoardService bellBoardService;
    @InjectMocks
    private BellBoardServiceImpl _bellBoardService;

    @Before
    public void setup()
    {
        performance1 = yorkshireMajorPerformance().build();
        performance2 = tritonDelightPerformance().build();
    }

    @Test
    public void testLoadPerformanceGivenId()
        throws Exception
    {
        given(bellBoardService.getSinglePerformance(ID)).willReturn(BBPERFORMANCE1);

        Performance performance = _bellBoardService.loadPerformance(ID);

        assertThat(performance, is(performance1));
    }

    @Test
    public void testLoadPerformanceReturnsNullForException()
        throws Exception
    {
        given(bellBoardService.getSinglePerformance(ID)).willThrow(Exception.class);

        Performance performance = _bellBoardService.loadPerformance(ID);

        assertThat(performance, is(nullValue()));
    }

    @Test
    public void testLoadPerformancesWithUrl()
        throws Exception
    {
        String searchUrl = "http://bb.ringingworld.co.uk/search.php?ringer=ryan+mills&length=q-or-p&bells_type=tower";
        String exportUrl = "http://bb.ringingworld.co.uk/export.php?ringer=ryan+mills&length=q-or-p&bells_type=tower";

        List<BBPerformance> performances = Arrays.asList(BBPERFORMANCE1, BBPERFORMANCE2);

        given(bellBoardService.getPerformances(exportUrl, null)).willReturn(performances);

        BellBoardImport bellBoardImport = new BellBoardImport(searchUrl);
        List<Performance> quarters = _bellBoardService.loadPerformances(bellBoardImport);

        assertThat(quarters, contains(performance1, performance2));
    }

    @Test
    public void testLoadPerformancesReturnsEmptyListWithInvalidUrl()
        throws Exception
    {
        String invalidUrl = "thisisnotaurl";
        given(bellBoardService.getPerformances(invalidUrl, null)).willThrow(Exception.class);
        BellBoardImport bellBoardImport = new BellBoardImport(invalidUrl);
        List<Performance> performances = _bellBoardService.loadPerformances(bellBoardImport);
        assertThat(performances, hasSize(0));
    }

}
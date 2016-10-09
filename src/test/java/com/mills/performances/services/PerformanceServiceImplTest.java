package com.mills.performances.services;

import com.mills.performances.AbstractTest;
import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.Location;
import com.mills.performances.models.Performance;
import com.mills.performances.services.impl.PerformanceServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static com.mills.performances.builders.PerformanceBuilder.tritonDelightPerformance;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by ryan on 23/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class PerformanceServiceImplTest extends AbstractTest {

    @InjectMocks
    private PerformanceServiceImpl _performanceService;

    private Performance _performance;

    @Before
    public void setup() {
        _performance = tritonDelightPerformance().build();
    }

    @Test
    public void comparesMatchingMethodForPerformance() {
        Boolean result = _performanceService.propertiesMatch(_performance, PerformanceProperty.METHOD, "Triton Delight");
        assertThat(result, is(true));
    }

    @Test
    public void comparesNonMatchingMethodForPerformance() {
        Boolean result = _performanceService.propertiesMatch(_performance, PerformanceProperty.METHOD, "Triton");
        assertThat(result, is(false));
    }

    @Test
    public void comparesMatchingStageForPerformance() {
        Boolean result = _performanceService.propertiesMatch(_performance, PerformanceProperty.STAGE, "Royal");
        assertThat(result, is(true));
    }

    @Test
    public void comparesNonMatchingStageForPerformance() {
        Boolean result = _performanceService.propertiesMatch(_performance, PerformanceProperty.STAGE, "R");
        assertThat(result, is(false));
    }

    @Test
    public void comparesMatchingRingerForPerformance() {
        Boolean result = _performanceService.propertiesMatch(_performance, PerformanceProperty.RINGER, "Ryan E Mills");
        assertThat(result, is(true));
    }

    @Test
    public void comparesNonMatchingRingerForPerformance() {
        Boolean result = _performanceService.propertiesMatch(_performance, PerformanceProperty.RINGER, "R");
        assertThat(result, is(false));
    }

    @Test
    public void throwsErrorForLocation() {
        try {
            _performanceService.propertiesMatch(_performance, PerformanceProperty.LOCATION, "Place");
            fail("Was expecting an exception");
        }
        catch(IllegalArgumentException e) {
            assertThat(true, is(true));
        }
    }

}
package com.mills.performances.builders;

import com.mills.bellboard.models.xml.BBPerformance;
import com.mills.performances.AbstractTest;
import com.mills.performances.models.Performance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static com.mills.performances.builders.PerformanceBuilder.performanceBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by ryan on 12/07/16.
 */
@RunWith(Parameterized.class)
public class PerformanceBuilderMethodTest
extends AbstractTest{

    @Parameters(name = "{index}: {0} => {1} {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { "Spliced Triples (4 Methods)", "Spliced (4m)", "Triples" },
            { "Spliced Triples (4 methods)", "Spliced (4m)", "Triples" },
            { "Spliced Triples (4m)", "Spliced (4m)", "Triples" },
            { "Stedman Caters", "Stedman", "Caters" },
            { "Doubles (3m/3v)", "(3m/3v)", "Doubles" },
            { "Triples (4 Methods)", "(4m)", "Triples" },
            { "Triples (4 methods)", "(4m)", "Triples" },
            { "Triples (4m)", "(4m)", "Triples" },
            { "Spliced Surprise Major (4 Methods)", "Spliced Surprise (4m)", "Major" },
            { "Spliced Surprise Major (4 methods)", "Spliced Surprise (4m)", "Major" },
            { "Spliced Surprise Major (4m)", "Spliced Surprise (4m)", "Major" },
            { "Surprise Major (4 Methods)", "Surprise (4m)", "Major" },
            { "Surprise Major (4 methods)", "Surprise (4m)", "Major" },
            { "Surprise Major (4m)", "Surprise (4m)", "Major" }
        });
    }

    private String inputString;
    private String expectedMethod;
    private String expectedStage;

    public PerformanceBuilderMethodTest(String input, String method, String stage) {
        inputString = input;
        expectedMethod = method;
        expectedStage = stage;
    }

    @Test
    public void canConvertMethod() {
        Performance performance = performanceBuilder().fromMethodString(inputString)
                                                      .build();

        assertThat(performance.getMethod(), is(expectedMethod));
        assertThat(performance.getStage(), is(expectedStage));
    }

}

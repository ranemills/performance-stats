package com.mills.performances.builders;

import com.mills.performances.AbstractTest;
import com.mills.performances.models.Performance;
import org.joda.time.DateTime;
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
public class PerformanceBuilderTimeTest
extends AbstractTest{

    @Parameters(name = "{index}: {0} => {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { "45", 45 },
            { "37 m", 37 },
            { "37m", 37 },
            { "5 mins", 5 },
            { "36mins", 36 },
            { "36 minutes", 36 },
            { "1h", 60 },
            { "0h53", 53 },
            { "2h06", 126 },
            { "3h08", 188 },
            { "2h6", 126 },
            { "2h35", 155 },
            { "2h56m", 176 },
            { "1hr", 60 },
            { "1hr 6min", 66 },
            { "1hrs 6min", 66 },
            { "1hrs 6min.", 66 },
            { "1 hr 6 min", 66 },
            { "1hr6min", 66 },
            { "1 Hour 4 mins", 64},
            { "1 hour 4 mins", 64},
            { "2 hours 4 mins", 124},
            { "2:41", 161},
            { "foobar", null}
        });
    }

    private String inputString;
    private Integer expected;

    public PerformanceBuilderTimeTest(String input, Integer expectedTime) {
        inputString = input;
        expected = expectedTime;
    }

    @Test
    public void canConvertMethod() {
        Performance performance = performanceBuilder().time(inputString)
                                                      .date(new DateTime(2016, 4, 10, 0, 0).toDate())
                                                      .build();

        assertThat(performance.getTime(), is(expected));
    }

}

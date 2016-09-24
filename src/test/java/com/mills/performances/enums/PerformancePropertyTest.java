package com.mills.performances.enums;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by ryan on 18/09/16.
 */
public class PerformancePropertyTest {

    @Test
    public void testToString() {
        assertThat(PerformanceProperty.LOCATION.toString(), is("LOCATION"));
        assertThat(PerformanceProperty.STAGE.toString(), is("STAGE"));
        assertThat(PerformanceProperty.METHOD.toString(), is("METHOD"));
    }

    @Test
    public void fromString() {
        assertThat(PerformanceProperty.fromString("stage"), is(PerformanceProperty.STAGE));
        assertThat(PerformanceProperty.fromString("Stage"), is(PerformanceProperty.STAGE));
        assertThat(PerformanceProperty.fromString("STAGE"), is(PerformanceProperty.STAGE));

        assertThat(PerformanceProperty.fromString("location"), is(PerformanceProperty.LOCATION));
        assertThat(PerformanceProperty.fromString("Location"), is(PerformanceProperty.LOCATION));
        assertThat(PerformanceProperty.fromString("LOCATION"), is(PerformanceProperty.LOCATION));

        assertThat(PerformanceProperty.fromString("method"), is(PerformanceProperty.METHOD));
        assertThat(PerformanceProperty.fromString("Method"), is(PerformanceProperty.METHOD));
        assertThat(PerformanceProperty.fromString("METHOD"), is(PerformanceProperty.METHOD));
    }

}

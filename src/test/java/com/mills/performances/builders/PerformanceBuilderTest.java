package com.mills.performances.builders;

import com.mills.bellboard.models.xml.BBPerformance;
import com.mills.performances.AbstractTest;
import com.mills.performances.models.Location;
import com.mills.performances.models.Performance;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.mills.bellboard.models.BBPerformanceBuilder.bbPerformanceBuilder;
import static com.mills.performances.builders.PerformanceBuilder.fromBBPeformance;
import static com.mills.performances.builders.PerformanceBuilder.performanceBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by ryan on 12/07/16.
 */
public class PerformanceBuilderTest extends AbstractTest {

    private static final String ID = "1995";
    private static final String ASSOCIATION = "None";
    private static final String PLACE = "Abingdon";
    private static final String DEDICATION = "Christ Church";
    private static final String COUNTY = "Oxfordshire";
    private static final String DATE_STRING = "2016-04-10";
    private static final Integer DATE_YEAR = 2016;
    private static final Date DATE_DATE = new DateTime(DATE_YEAR, 4, 10, 0, 0).toDate();
    private static final String METHOD = "Yorkshire Surprise";
    private static final String STAGE = "Major";
    private static final int CHANGES = 1280;
    private static final String RINGER_1 = "Rebecca Franklin";
    private static final String RINGER_2 = "Brian Read";
    private static final String RINGER_3 = "Susan Read";
    private static final String RINGER_4 = "Sarah Barnes";
    private static final String RINGER_5 = "David Thomas";
    private static final String RINGER_6 = "Matthew Franklin";

    @Test
    public void canConvertBBPerformance()
    {
        BBPerformance bbPerformance = bbPerformanceBuilder().id(ID)
                                                            .association(ASSOCIATION)
                                                            .place(PLACE)
                                                            .dedication(DEDICATION)
                                                            .county(COUNTY)
                                                            .date(DATE_STRING)
                                                            .ringer(1, RINGER_1)
                                                            .ringer(2, RINGER_2)
                                                            .ringer(3, RINGER_3)
                                                            .ringer(4, RINGER_4)
                                                            .ringer(5, RINGER_5, true)
                                                            .ringer(6, RINGER_6)
                                                            .changes(CHANGES)
                                                            .method(METHOD + " " + STAGE)
                                                            .build();

        Performance expectedPerformance = performanceBuilder().bellboardId(ID)
                                                              .date(DATE_DATE)
                                                              .year(DATE_YEAR)
                                                              .changes(CHANGES)
                                                              .method(METHOD)
                                                              .stage(STAGE)
                                                              .location(DEDICATION, PLACE, COUNTY)
                                                              .ringer(1, RINGER_1)
                                                              .ringer(2, RINGER_2)
                                                              .ringer(3, RINGER_3)
                                                              .ringer(4, RINGER_4)
                                                              .ringer(5, RINGER_5, true)
                                                              .ringer(6, RINGER_6)
                                                              .build();


        Performance performance = fromBBPeformance(bbPerformance).build();

        assertThat(performance, is(expectedPerformance));
    }

}

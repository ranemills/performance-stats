package com.mills.bellboard.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mills.bellboard.models.xml.BBPerformance;
import com.mills.bellboard.services.impl.BellBoardServiceImpl;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mills.bellboard.models.BBPerformanceBuilder.bbPerformanceBuilder;
import static com.mills.bellboard.services.BellBoardServiceImplTest.XmlBuilder.inputStreamFromBuilders;
import static com.mills.bellboard.services.BellBoardServiceImplTest.XmlBuilder.xmlBuilder;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by ryan on 12/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BellBoardServiceImplTest {

    private static final String ID = "1995";
    private static final String ASSOCIATION = "None";
    private static final String PLACE = "Abingdon";
    private static final String DEDICATION = "Christ Church";
    private static final String COUNTY = "Oxfordshire";
    private static final String DATE = "2016-04-10";
    private static final String METHOD = "Yorkshire Surprise Major";
    private static final int CHANGES = 1280;
    private static final String RINGER_1 = "Rebecca Franklin";
    private static final String RINGER_2 = "Brian Read";
    private static final String RINGER_3 = "Susan Read";
    private static final String RINGER_4 = "Sarah Barnes";
    private static final String RINGER_5 = "David Thomas";
    private static final String RINGER_6 = "Matthew Franklin";

    @Mock
    private BellBoardHttpService _bellBoardHttpService;
    @InjectMocks
    private BellBoardServiceImpl _bellBoardService;

    @Test
    public void canSerializeSinglePerformance()
        throws Exception
    {
        InputStream performanceIS = xmlBuilder().id(ID)
                                                .association(ASSOCIATION)
                                                .place(PLACE)
                                                .dedication(DEDICATION)
                                                .county(COUNTY)
                                                .ringType("tower")
                                                .ringTenor("16-0-0 in F")
                                                .date(DATE)
                                                .time("44 mins")
                                                .changes(String.valueOf(CHANGES))
                                                .method(METHOD)
                                                .ringer(1, RINGER_1)
                                                .ringer(2, RINGER_2)
                                                .ringer(3, RINGER_3)
                                                .ringer(4, RINGER_4)
                                                .ringer(5, RINGER_5, true)
                                                .ringer(6, RINGER_6)
                                                .buildInputStream();

        BBPerformance expectedPerformance = bbPerformanceBuilder().id(ID)
                                                                  .association(ASSOCIATION)
                                                                  .place(PLACE)
                                                                  .dedication(DEDICATION)
                                                                  .county(COUNTY)
                                                                  .date(DATE)
                                                                  .ringer(1, RINGER_1)
                                                                  .ringer(2, RINGER_2)
                                                                  .ringer(3, RINGER_3)
                                                                  .ringer(4, RINGER_4)
                                                                  .ringer(5, RINGER_5, true)
                                                                  .ringer(6, RINGER_6)
                                                                  .changes(CHANGES)
                                                                  .method(METHOD)
                                                                  .build();

        given(_bellBoardHttpService.getPerformance(ID)).willReturn(performanceIS);

        BBPerformance singlePerformance = _bellBoardService.getSinglePerformance(ID);

        assertThat(singlePerformance.getBellboadId(), equalTo(expectedPerformance.getBellboadId()));
        assertThat(singlePerformance.getDate(), equalTo(expectedPerformance.getDate()));
        assertThat(singlePerformance.getPlace(), equalTo(expectedPerformance.getPlace()));
        assertThat(singlePerformance.getRingers(), equalTo(expectedPerformance.getRingers()));
        assertThat(singlePerformance.getTitle(), equalTo(expectedPerformance.getTitle()));

        assertThat(singlePerformance, equalTo(expectedPerformance));
    }

    @Test
    public void canSerializeMultiplePerformances()
        throws Exception
    {
        String id2 = "2022";
        String association2 = "Oxford Society";
        String place2 = "Oxford";
        String dedication2 = "St Helen";
        String county2 = "Oxfordshire";
        String date2 = "2016-04-12";
        String method2 = "Stedman Triples";
        int changes2 = 1250;

        XmlBuilder performanceIS = xmlBuilder().id(ID)
                                               .association(ASSOCIATION)
                                               .place(PLACE)
                                               .dedication(DEDICATION)
                                               .county(COUNTY)
                                               .ringType("tower")
                                               .ringTenor("16-0-0 in F")
                                               .date(DATE)
                                               .time("44 mins")
                                               .changes(String.valueOf(CHANGES))
                                               .method(METHOD)
                                               .ringer(1, RINGER_1)
                                               .ringer(2, RINGER_2)
                                               .ringer(3, RINGER_3)
                                               .ringer(4, RINGER_4)
                                               .ringer(5, RINGER_5, true)
                                               .ringer(6, RINGER_6);

        XmlBuilder performanceIS2 = xmlBuilder().id(id2)
                                                .association(association2)
                                                .place(place2)
                                                .dedication(dedication2)
                                                .county(county2)
                                                .ringType("tower")
                                                .ringTenor("16-0-0 in F")
                                                .date(date2)
                                                .time("44 mins")
                                                .changes(String.valueOf(changes2))
                                                .method(method2)
                                                .ringer(1, RINGER_1)
                                                .ringer(2, RINGER_2)
                                                .ringer(3, RINGER_3)
                                                .ringer(4, RINGER_4)
                                                .ringer(5, RINGER_5, true)
                                                .ringer(6, RINGER_6);


        BBPerformance expectedPerformance = bbPerformanceBuilder().id(ID)
                                                                  .association(ASSOCIATION)
                                                                  .place(PLACE)
                                                                  .dedication(DEDICATION)
                                                                  .county(COUNTY)
                                                                  .date(DATE)
                                                                  .ringer(1, RINGER_1)
                                                                  .ringer(2, RINGER_2)
                                                                  .ringer(3, RINGER_3)
                                                                  .ringer(4, RINGER_4)
                                                                  .ringer(5, RINGER_5, true)
                                                                  .ringer(6, RINGER_6)
                                                                  .changes(CHANGES)
                                                                  .method(METHOD)
                                                                  .build();

        BBPerformance expectedPerformance2 = bbPerformanceBuilder().id(id2)
                                                                   .association(association2)
                                                                   .place(place2)
                                                                   .dedication(dedication2)
                                                                   .county(county2)
                                                                   .date(date2)
                                                                   .ringer(1, RINGER_1)
                                                                   .ringer(2, RINGER_2)
                                                                   .ringer(3, RINGER_3)
                                                                   .ringer(4, RINGER_4)
                                                                   .ringer(5, RINGER_5, true)
                                                                   .ringer(6, RINGER_6)
                                                                   .changes(changes2)
                                                                   .method(method2)
                                                                   .build();

        String rawUrl = "http://example.com";
        DateTime changedSince = DateTime.now().minusDays(1);

        InputStream returnedInputStream = inputStreamFromBuilders(ImmutableList.<XmlBuilder>builder().add(performanceIS).add(performanceIS2).build());

        given(_bellBoardHttpService.getPerformances(rawUrl, changedSince)).willReturn(returnedInputStream);

        List<BBPerformance> performances = _bellBoardService.getPerformances(rawUrl, changedSince);

        assertThat(performances, hasItems(expectedPerformance, expectedPerformance2));
    }


    public static class XmlBuilder {

        private String id = "1995";
        private String association = "St. Martin's Guild for the Diocese of Birmingham";
        private String place = "Birmingham";
        private String dedication = "St Paul, Jewellery Quarter";
        private String county = "West Midlands";
        private String ringType = "tower";
        private String ringTenor = "12-2-12";
        private String date = "2009-12-02";
        private String time = "5h 21m";
        private String changes = "10080";
        private String method = "Stedman Triples";
        private String composer = "R W Pipe (7 Part)";
        private Map<Integer, String> ringers;
        private List<Integer> conductors;

        static XmlBuilder xmlBuilder() {

            return new XmlBuilder();
        }

        static InputStream inputStreamFromBuilders(List<XmlBuilder> builders)
        {
            String xml = "<?xml version=\"1.0\"?> <performances xmlns=\"http://bb.ringingworld.co" +
                         ".uk/NS/performances#\">";
            for (XmlBuilder performance : builders) {
                xml = xml + performance.buildString();
            }
            xml = xml + "</performances>";
            return new ByteArrayInputStream(xml.getBytes());
        }

        XmlBuilder association(String association) {
            this.association = association;
            return this;
        }

        XmlBuilder place(String place) {
            this.place = place;
            return this;
        }

        XmlBuilder dedication(String dedication) {
            this.dedication = dedication;
            return this;
        }

        XmlBuilder county(String county) {
            this.county = county;
            return this;
        }

        XmlBuilder ringType(String ringType) {
            this.ringType = ringType;
            return this;
        }

        XmlBuilder ringTenor(String ringTenor) {
            this.ringTenor = ringTenor;
            return this;
        }

        XmlBuilder date(String date) {
            this.date = date;
            return this;
        }

        XmlBuilder time(String time) {
            this.time = time;
            return this;
        }

        XmlBuilder changes(String changes) {
            this.changes = changes;
            return this;
        }

        XmlBuilder method(String method) {
            this.method = method;
            return this;
        }

        XmlBuilder ringer(Integer bell, String name) {
            if (this.ringers == null) {
                this.ringers = new HashMap<>();
            }
            this.ringers.put(bell, name);
            return this;
        }

        XmlBuilder ringer(Integer bell, String name, Boolean conductor) {
            ringer(bell, name);
            if (conductor) {
                if (conductors == null) {
                    conductors = new ArrayList<>();
                }

                conductors.add(bell);
            }
            return this;
        }

        XmlBuilder id(String id) {
            this.id = id;
            return this;
        }

        InputStream buildInputStream() {
            return new ByteArrayInputStream(buildString().getBytes());
        }

        String buildString() {
            if (ringers == null) {
                ringers = ImmutableMap.<Integer, String>builder()
                              .put(1, "Christine Mills")
                              .put(2, "Mark R Eccleston")
                              .put(3, "Maurice F Edwards")
                              .put(4, "Jonathan P Healy")
                              .put(5, "Richard L Jones")
                              .put(6, "Paul E Bibilo")
                              .put(7, "Michael P A Wilby")
                              .put(8, "Richard B Grimmett")
                              .build();
            }

            if (conductors == null) {
                conductors = ImmutableList.<Integer>builder().add(2).build();
            }

            String xml = "<performance xmlns=\"http://bb.ringingworld.co.uk/NS/performances#\" id=\"" + id + "\">\n" +
                         "  <association>" + association + "</association>\n" +
                         "  <place>\n" +
                         "    <place-name type=\"place\">" + place + "</place-name>\n" +
                         "    <place-name type=\"dedication\">" + dedication + "</place-name>\n" +
                         "    <place-name type=\"county\">" + county + "</place-name>\n" +
                         "    <ring type=\"" + ringType + "\" tenor=\"" + ringTenor + "\" />\n" +
                         "  </place>\n" +
                         "  <date>" + date + "</date>\n" +
                         "  <duration>" + time + "</duration>\n" +
                         "  <title><changes>" + changes + "</changes> <method>" + method + "</method></title>\n" +
                         "  <composer>" + composer + "</composer>\n" +
                         "  <ringers>\n";
            for (Map.Entry<Integer, String> ringer : ringers.entrySet()) {
                xml = xml + "<ringer bell=\"" + ringer.getKey() + "\"";
                if (conductors.contains(ringer.getKey())) {
                    xml = xml + " conductor=\"true\" ";
                }
                xml = xml + ">" + ringer.getValue() + "</ringer>\n";
            }
            xml = xml +
                  "    </ringers>\n" +
                  "  <timestamp>2015-07-28T12:49:33</timestamp>\n" +
                  "</performance>";

            return xml;
        }
    }

}

package com.mills.performances.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mills.bellboard.models.xml.BBPerformance;
import com.mills.bellboard.models.xml.BBPerformanceList;
import com.mills.performances.AbstractTest;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.Performance;
import com.mills.performances.services.impl.BellBoardServiceImpl;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mills.performances.builders.PerformanceBuilder.performanceBuilder;
import static com.mills.performances.services.BellBoardServiceTest.XmlBuilder.xmlBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

/**
 * Created by ryan on 23/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BellBoardServiceTest extends AbstractTest {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");

    @Mock
    private com.mills.bellboard.services.BellBoardService bellBoardService;
    @InjectMocks
    private BellBoardServiceImpl _bellBoardService;

    @Test
    public void testAddPerformanceGivenId()
        throws Exception
    {
        String id = "1995";

        BBPerformance performanceXml = xmlBuilder().id("1995")
                                                   .association("")
                                                   .place("Abingdon")
                                                   .dedication("St Helen")
                                                   .county("Oxfordshire")
                                                   .ringType("tower")
                                                   .ringTenor("16-0-0 in F")
                                                   .date("2016-04-10")
                                                   .time("44 mins")
                                                   .changes("1280")
                                                   .method("Yorkshire Surprise Major")
                                                   .ringer(1, "Rebecca Franklin")
                                                   .ringer(2, "Brian Read")
                                                   .ringer(3, "Susan Read")
                                                   .ringer(4, "Sarah Barnes")
                                                   .ringer(5, "David Thomas", true)
                                                   .ringer(6, "Matthew Franklin")
                                                   .ringer(7, "Tim Pett")
                                                   .ringer(8, "Ryan Mills")
                                                   .buildXml();

        Performance expectedPerformance = performanceBuilder()
                                              .bellboardId("1995")
                                              .date(SDF.parse("10-04-2016"))
                                              .location("Abingdon")
                                              .changes(1280)
                                              .method("Yorkshire Surprise")
                                              .stage("Major")
                                              .ringer(1, "Rebecca Franklin")
                                              .ringer(2, "Brian Read")
                                              .ringer(3, "Susan Read")
                                              .ringer(4, "Sarah Barnes")
                                              .ringer(5, "David Thomas", true)
                                              .ringer(6, "Matthew Franklin")
                                              .ringer(7, "Tim Pett")
                                              .ringer(8, "Ryan Mills")
                                              .build();

        given(bellBoardService.getSinglePerformance(id)).willReturn(performanceXml);

        Performance performance = _bellBoardService.getPerformance(id);

        assertThat(performance, is(expectedPerformance));
    }

    @Test
    public void testAddPerformancesWithUrl()
        throws Exception
    {
        String searchUrl = "http://bb.ringingworld.co.uk/search.php?ringer=ryan+mills&length=q-or-p&bells_type=tower";
        String exportUrl = "http://bb.ringingworld.co.uk/export.php?ringer=ryan+mills&length=q-or-p&bells_type=tower";

        String p1 = xmlBuilder().id("101")
                                .association("")
                                .place("Abingdon")
                                .dedication("St Helen")
                                .county("Oxfordshire")
                                .ringType("tower")
                                .ringTenor("16-0-0 in F")
                                .date("2016-04-10")
                                .time("44 mins")
                                .changes("1280")
                                .method("Yorkshire Surprise Major")
                                .ringer(1, "Rebecca Franklin")
                                .ringer(2, "Brian Read")
                                .ringer(3, "Susan Read")
                                .ringer(4, "Sarah Barnes")
                                .ringer(5, "David Thomas", true)
                                .ringer(6, "Matthew Franklin")
                                .ringer(7, "Tim Pett")
                                .ringer(8, "Ryan Mills")
                                .buildString();

        String p2 = xmlBuilder().id("1500")
                                .association("Oxford Society")
                                .place("Oxford")
                                .dedication("Christ Church")
                                .county("Oxfordshire")
                                .ringType("tower")
                                .ringTenor("31-0-23")
                                .date("2016-03-21")
                                .time("1h00")
                                .changes("1440")
                                .method("Triton Delight Royal")
                                .ringer(1, "Bernard J Stone")
                                .ringer(2, "Robin O Hall", true)
                                .ringer(3, "Michele Winter")
                                .ringer(4, "Ryan E Mills")
                                .ringer(5, "Stephen M Jones")
                                .ringer(6, "Stuart F Gibson")
                                .ringer(7, "Elizabeth C Frye")
                                .ringer(8, "Michael A Williams")
                                .ringer(9, "Mark D Tarrant")
                                .ringer(10, "Colin M Lee")
                                .buildString();

        List<BBPerformance> performances = XmlBuilder.xmlList(Arrays.asList(p1, p2));

        given(bellBoardService.getPerformances(exportUrl, null)).willReturn(performances);

        Performance expectedPerformance1 = performanceBuilder().bellboardId("101")
                                                               .date(SDF.parse("10-04-2016"))
                                                               .location("Abingdon")
                                                               .changes(1280)
                                                               .method("Yorkshire Surprise")
                                                               .stage("Major")
                                                               .ringer(1, "Rebecca Franklin")
                                                               .ringer(2, "Brian Read")
                                                               .ringer(3, "Susan Read")
                                                               .ringer(4, "Sarah Barnes")
                                                               .ringer(5, "David Thomas", true)
                                                               .ringer(6, "Matthew Franklin")
                                                               .ringer(7, "Tim Pett")
                                                               .ringer(8, "Ryan Mills")
                                                               .build();
        Performance expectedPerformance2 = performanceBuilder().bellboardId("1500")
                                                               .date(SDF.parse("21-03-2016"))
                                                               .location("Oxford")
                                                               .changes(1440)
                                                               .method("Triton Delight")
                                                               .stage("Royal")
                                                               .ringer(1, "Bernard J Stone")
                                                               .ringer(2, "Robin O Hall", true)
                                                               .ringer(3, "Michele Winter")
                                                               .ringer(4, "Ryan E Mills")
                                                               .ringer(5, "Stephen M Jones")
                                                               .ringer(6, "Stuart F Gibson")
                                                               .ringer(7, "Elizabeth C Frye")
                                                               .ringer(8, "Michael A Williams")
                                                               .ringer(9, "Mark D Tarrant")
                                                               .ringer(10, "Colin M Lee")
                                                               .build();

        List<Performance> expectedPerformances = ImmutableList.<Performance>builder().add(expectedPerformance1)
                                                                                     .add(expectedPerformance2)
                                                                                     .build();

        BellBoardImport bellBoardImport = new BellBoardImport(searchUrl);
        List<Performance> quarters = _bellBoardService.getPerformances(bellBoardImport);

        assertThat(quarters, containsInAnyOrder(expectedPerformances.toArray()));
    }

    @Test
    public void testAddPerformancesReturnsEmptyListWithInvalidUrl()
        throws Exception
    {
        String invalidUrl = "thisisnotaurl";
        given(bellBoardService.getPerformances(invalidUrl, null)).willThrow(Exception.class);
        BellBoardImport bellBoardImport = new BellBoardImport(invalidUrl);
        List<Performance> performances = _bellBoardService.getPerformances(bellBoardImport);
        assertThat(performances, hasSize(0));
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
        private Map<Integer, String> ringers; //TODO: Do this
        private List<Integer> conductors;

        public static XmlBuilder xmlBuilder() {

            return new XmlBuilder();
        }

        public static String performanceListString(List<String> performances) {
            String xml = "<?xml version=\"1.0\"?> <performances xmlns=\"http://bb.ringingworld.co" +
                         ".uk/NS/performances#\">";
            for (String performance : performances) {
                xml = xml + performance;
            }
            xml = xml + "</performances>";
            return xml;
        }

        public static InputStream performanceListInputStream(List<String> performances) {
            return new ByteArrayInputStream(performanceListString(performances).getBytes());
        }

        public static List<BBPerformance> xmlList(List<String> performances)
            throws Exception
        {
            String output = IOUtils.toString(performanceListInputStream(performances));
            Serializer serializer = new Persister();
            return serializer.read(BBPerformanceList.class, output, false).getPerformances();

        }

        public XmlBuilder association(String association) {
            this.association = association;
            return this;
        }

        public XmlBuilder place(String place) {
            this.place = place;
            return this;
        }

        public XmlBuilder dedication(String dedication) {
            this.dedication = dedication;
            return this;
        }

        public XmlBuilder county(String county) {
            this.county = county;
            return this;
        }

        public XmlBuilder ringType(String ringType) {
            this.ringType = ringType;
            return this;
        }

        public XmlBuilder ringTenor(String ringTenor) {
            this.ringTenor = ringTenor;
            return this;
        }

        public XmlBuilder date(String date) {
            this.date = date;
            return this;
        }

        public XmlBuilder time(String time) {
            this.time = time;
            return this;
        }

        public XmlBuilder changes(String changes) {
            this.changes = changes;
            return this;
        }

        public XmlBuilder method(String method) {
            this.method = method;
            return this;
        }

        public XmlBuilder composer(String composer) {
            this.composer = composer;
            return this;
        }

        public XmlBuilder ringers(Map<Integer, String> ringers) {
            this.ringers = ringers;
            return this;
        }

        public XmlBuilder ringer(Integer bell, String name) {
            if (this.ringers == null) {
                this.ringers = new HashMap<>();
            }
            this.ringers.put(bell, name);
            return this;
        }

        public XmlBuilder ringer(Integer bell, String name, Boolean conductor) {
            ringer(bell, name);
            if (conductor) {
                if (conductors == null) {
                    conductors = new ArrayList<>();
                }

                conductors.add(bell);
            }
            return this;
        }

        public XmlBuilder conductors(List<Integer> conductors) {
            this.conductors = conductors;
            return this;
        }

        public XmlBuilder id(String id) {
            this.id = id;
            return this;
        }

        BBPerformance buildXml()
            throws Exception
        {
            String output = IOUtils.toString(buildInputStream());
            Serializer serializer = new Persister();
            return serializer.read(BBPerformance.class, output, false);
        }

        InputStream buildInputStream() {
            return new ByteArrayInputStream(buildString().getBytes());
        }

        public String buildString() {
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


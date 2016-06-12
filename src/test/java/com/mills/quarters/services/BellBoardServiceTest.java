package com.mills.quarters.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mills.quarters.AbstractTest;
import com.mills.quarters.models.BellBoardImport;
import com.mills.quarters.models.Quarter;
import com.mills.quarters.repositories.QuarterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mills.quarters.builders.QuarterBuilder.quarterBuilder;
import static com.mills.quarters.services.BellBoardServiceTest.XmlBuilder.xmlBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by ryan on 23/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BellBoardServiceTest extends AbstractTest {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
    @Mock
    private BellBoardHttpService bellBoardHttpService;
    @Mock
    private QuarterRepository quarterRepository;
    @InjectMocks
    private BellBoardService _bellBoardService;

    @Test
    public void testAddPerformanceGivenId()
        throws Exception
    {
        String id = "1995";

        InputStream performanceXml = xmlBuilder().id("1995")
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
                                                 .buildInputStream();

        Quarter expectedQuarter = quarterBuilder()
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

        given(bellBoardHttpService.getPerformance(id)).willReturn(performanceXml);

        Quarter quarter = _bellBoardService.quarterFromBBPerformance(id);

        assertThat(quarter, is(expectedQuarter));
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

        InputStream performances = XmlBuilder.performanceListInputStream(Arrays.asList(p1, p2));

        given(bellBoardHttpService.getPerformances(exportUrl, null)).willReturn(performances);

        Quarter expectedQuarter1 = quarterBuilder().bellboardId("101")
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
        Quarter expectedQuarter2 = quarterBuilder().bellboardId("1500")
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

        List<Quarter> expectedQuarters = ImmutableList.<Quarter>builder().add(expectedQuarter1)
                                                                         .add(expectedQuarter2)
                                                                         .build();

        BellBoardImport bellBoardImport = new BellBoardImport(searchUrl);
        List<Quarter> quarters = _bellBoardService.addPerformances(bellBoardImport);

        verify(quarterRepository).save(expectedQuarters);
    }

    @Test
    public void testAddPerformancesFailsWithInvalidUrl()
        throws Exception
    {
        String invalidUrl = "thisisnotaurl";
        given(bellBoardHttpService.getPerformances(invalidUrl, null)).willThrow(URISyntaxException.class);
        try {
            BellBoardImport bellBoardImport = new BellBoardImport(invalidUrl);
            _bellBoardService.addPerformances(bellBoardImport);
            fail("Should not be able to add a performance with an invalid URL");
        } catch (URISyntaxException e) {
            assertThat(true, is(true));
        }
    }

    @Test
    public void testAddPerformanceWithStedman()
        throws Exception
    {
        Quarter quarter = getQuarterWithSpecifiedMethod("Stedman Caters");

        assertThat(quarter.getMethod(), equalTo("Stedman"));
        assertThat(quarter.getStage(), equalTo("Caters"));
    }

    @Test
    public void testAddPerformanceWithSplicedMethodsShortMethodAndVariation()
        throws Exception
    {
        Quarter quarter = getQuarterWithSpecifiedMethod("Doubles (3m/3v)");

        assertThat(quarter.getMethod() + " " + quarter.getStage(), equalTo("(3m/3v) Doubles"));
        assertThat(quarter.getMethod(), equalTo("(3m/3v)"));
        assertThat(quarter.getStage(), equalTo("Doubles"));
    }

    @Test
    public void testSplicedMethodsNoClassQualifier()
        throws Exception
    {
        List<String> equivalentStrings = ImmutableList.<String>builder()
                                             .add("Spliced Triples (4 Methods)")
                                             .add("Spliced Triples (4 methods)")
                                             .add("Spliced Triples (4m)")
                                             .build();

        for (String methodString : equivalentStrings) {
            Quarter quarter = getQuarterWithSpecifiedMethod(methodString);
            assertThat(quarter.getStage(), equalTo("Triples"));
            assertThat(quarter.getMethod(), equalTo("Spliced (4m)"));
        }
    }

    @Test
    public void testMixedMethodsNoClassQualifier()
        throws Exception
    {
        List<String> equivalentStrings = ImmutableList.<String>builder()
                                             .add("Triples (4 Methods)")
                                             .add("Triples (4 methods)")
                                             .add("Triples (4m)")
                                             .build();

        for (String methodString : equivalentStrings) {
            Quarter quarter = getQuarterWithSpecifiedMethod(methodString);
            assertThat(quarter.getStage(), equalTo("Triples"));
            assertThat(quarter.getMethod(), equalTo("(4m)"));
        }
    }

    @Test
    public void testSplicedMethodsWithClassQualifier()
        throws Exception
    {
        List<String> equivalentStrings = ImmutableList.<String>builder()
                                             .add("Spliced Surprise Major (4 Methods)")
                                             .add("Spliced Surprise Major (4 methods)")
                                             .add("Spliced Surprise Major (4m)")
                                             .build();

        for (String methodString : equivalentStrings) {
            Quarter quarter = getQuarterWithSpecifiedMethod(methodString);
            assertThat(quarter.getStage(), equalTo("Major"));
            assertThat(quarter.getMethod(), equalTo("Spliced Surprise (4m)"));
        }
    }

    @Test
    public void testMixedMethodsWithClassQualifier()
        throws Exception
    {
        List<String> equivalentStrings = ImmutableList.<String>builder()
                                             .add("Surprise Major (4 Methods)")
                                             .add("Surprise Major (4 methods)")
                                             .add("Surprise Major (4m)")
                                             .build();

        for (String methodString : equivalentStrings) {
            Quarter quarter = getQuarterWithSpecifiedMethod(methodString);
            assertThat(quarter.getStage(), equalTo("Major"));
            assertThat(quarter.getMethod(), equalTo("Surprise (4m)"));
        }
    }

    private Quarter getQuarterWithSpecifiedMethod(String methodString)
        throws Exception
    {
        String id = "id";
        InputStream performance = xmlBuilder().method(methodString).buildInputStream();
        given(bellBoardHttpService.getPerformance(id)).willReturn(performance);

        return _bellBoardService.quarterFromBBPerformance(id);
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


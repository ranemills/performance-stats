package com.mills.quarters.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mills.quarters.models.Quarter;
import com.mills.quarters.repositories.QuarterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.mills.quarters.builders.QuarterBuilder.quarterBuilder;
import static com.mills.quarters.services.BellBoardServiceTest.XmlBuilder.xmlBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by ryan on 23/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BellBoardServiceTest {

    @Mock
    BellBoardHttpService bellBoardHttpService;

    @Mock
    QuarterRepository quarterRepository;

    @InjectMocks
    BellBoardService _bellBoardService;

    @Test
    public void testAddPerformances()
        throws Exception
    {
        String p1 = xmlBuilder().association("")
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
                                .ringer(8, "Ryan Mills").buildString();

        String p2 = xmlBuilder().association("Oxford Society")
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

        given(bellBoardHttpService.getPerformances()).willReturn(performances);

        Quarter expectedQuarter1 = quarterBuilder()
                                       .date(new SimpleDateFormat("YYYY-MM-dd").parse("2016-04-10"))
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
        Quarter expectedQuarter2 = quarterBuilder()
                                       .date(new SimpleDateFormat("YYYY-MM-dd").parse("2016-03-31"))
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

        List<Quarter> expectedQuarters = ImmutableList.<Quarter>builder().add(expectedQuarter1).add(expectedQuarter2)
                                                                         .build();

        List<Quarter> quarters = _bellBoardService.addPerformances();

        assertThat(quarters, is(expectedQuarters));
    }

    @Test
    public void testAddPerformanceGivenId()
        throws Exception
    {
        String id = "1995";
        String xml = "<?xml version=\"1.0\"?>\n" +
                     "<performance xmlns=\"http://bb.ringingworld.co.uk/NS/performances#\" id=\"1995\">\n" +
                     "  <association>St. Martin's Guild for the Diocese of Birmingham</association>\n" +
                     "  <place>\n" +
                     "    <place-name type=\"place\">Birmingham</place-name>\n" +
                     "    <place-name type=\"dedication\">St Paul, Jewellery Quarter</place-name>\n" +
                     "    <place-name type=\"county\">West Midlands</place-name>\n" +
                     "    <ring type=\"tower\" tenor=\"12-2-13\" />\n" +
                     "  </place>\n" +
                     "  <date>2009-12-02</date>\n" +
                     "  <duration>5h 21m</duration>\n" +
                     "  <title><changes>10080</changes> <method>Stedman Triples</method></title>\n" +
                     "  <details>Each change occurs once at handstroke and once at backstroke</details>\n" +
                     "  <composer>R W Pipe (7 Part)</composer>\n" +
                     "  <ringers>\n" +
                     "      <ringer bell=\"1\">Christine Mills</ringer>\n" +
                     "      <ringer bell=\"2\" conductor=\"true\">Mark R Eccleston</ringer>\n" +
                     "      <ringer bell=\"3\">Maurice F Edwards</ringer>\n" +
                     "      <ringer bell=\"4\">Jonathan P Healy</ringer>\n" +
                     "      <ringer bell=\"5\">Richard L Jones</ringer>\n" +
                     "      <ringer bell=\"6\">Paul E Bibilo</ringer>\n" +
                     "      <ringer bell=\"7\">Michael P A Wilby</ringer>\n" +
                     "      <ringer bell=\"8\">Richard B Grimmett</ringer>\n" +
                     "    </ringers>\n" +
                     "  <footnote>Rung for the first time. This peal was originally composed in June 1980 at the " +
                     "request of John McDonald. John arranged a few unsuccessful attempts to ring it in the early" +
                     " 1980s. This completes some Birmingham unfinished business.</footnote>\n" +
                     "  <timestamp>2015-07-28T12:49:33</timestamp>\n" +
                     "  <source site=\"Campanophile\" ref=\"0x16C81\" />\n" +
                     "</performance>";

        Quarter expectedQuarter = quarterBuilder().date(new SimpleDateFormat("YYYY-MM-dd").parse("2009-12-02"))
                                                  .changes(10080)
                                                  .location("Birmingham")
                                                  .method("Stedman")
                                                  .stage("Triples")
                                                  .ringer(1, "Christine Mills")
                                                  .ringer(2, "Mark R Eccleston", true)
                                                  .ringer(3, "Maurice F Edwards")
                                                  .ringer(4, "Jonathan P Healy")
                                                  .ringer(5, "Richard L Jones")
                                                  .ringer(6, "Paul E Bibilo")
                                                  .ringer(7, "Michael P A Wilby")
                                                  .ringer(8, "Richard B Grimmett")
                                                  .build();

        given(bellBoardHttpService.getPerformance(id)).willReturn(xmlBuilder().buildInputStream());

        Quarter quarter = _bellBoardService.addPerformance(id);

        assertThat(quarter, is(expectedQuarter));
    }

    protected static class XmlBuilder {

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

        static XmlBuilder xmlBuilder() {

            return new XmlBuilder();
        }

        static String performanceListString(List<String> performances) {
            String xml = "<?xml version=\"1.0\"?> <performances xmlns=\"http://bb.ringingworld.co" +
                         ".uk/NS/performances#\">";
            for (String performance : performances) {
                xml = xml + performance;
            }
            xml = xml + "</performances>";
            System.out.println(xml);
            return xml;
        }

        static InputStream performanceListInputStream(List<String> performances) {
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
            if (conductors == null) {
                conductors = new ArrayList<>();
            }
            conductors.add(bell);
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

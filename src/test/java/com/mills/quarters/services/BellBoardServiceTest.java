package com.mills.quarters.services;

import com.google.common.collect.ImmutableList;
import com.mills.quarters.models.Quarter;
import com.mills.quarters.repositories.QuarterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.mills.quarters.builders.QuarterBuilder.quarterBuilder;
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
    public void testAddPerformances() throws Exception {
        String xml = "<?xml version=\"1.0\"?> <performances xmlns=\"http://bb.ringingworld.co.uk/NS/performances#\"> " +
                         "<performance xmlns=\"http://bb.ringingworld.co.uk/NS/performances#\" id=\"P1001593\"> " +
                         "  <association></association> " +
                         "  <place> " +
                         "    <place-name type=\"place\">Abingdon</place-name> " +
                         "    <place-name type=\"dedication\">St Helen</place-name> " +
                         "    <place-name type=\"county\">Oxfordshire</place-name> " +
                             "<ring type=\"tower\" tenor=\"16-0-0 in F\" /> " +
                         "  </place> " +
                         "  <date>2016-04-10</date> " +
                         "  <duration>44 mins</duration> " +
                         "  <title>" +
                         "    <changes>1280</changes> " +
                         "    <method>Yorkshire Surprise Major</method>" +
                         "  </title> " +
                         "  <ringers> " +
                         "    <ringer bell=\"1\">Rebecca Franklin</ringer> " +
                         "    <ringer bell=\"2\">Brian Read</ringer> " +
                         "    <ringer bell=\"3\">Susan Read</ringer> " +
                         "    <ringer bell=\"4\">Sarah Barnes</ringer> " +
                         "    <ringer bell=\"5\" conductor=\"true\">David Thomas</ringer> " +
                         "    <ringer bell=\"6\">Matthew Franklin</ringer> " +
                         "   <ringer bell=\"7\">Tim Pett</ringer> " +
                         "   <ringer bell=\"8\">Ryan Mills</ringer> " +
                         "  </ringers> " +
                         "  <footnote>First in method: 4.</footnote> " +
                         "  <footnote>First Major as Conductor: 5.</footnote> " +
                         "  <footnote>Rung for the Evening Service.</footnote> " +
                         "  <timestamp>2016-04-10T16:51:37</timestamp> " +
                         "</performance> " +
                         "<performance xmlns=\"http://bb.ringingworld.co.uk/NS/performances#\" id=\"P1000458\"> " +
                         "  <association>Oxford Society</association> " +
                         "  <place> " +
                         "    <place-name type=\"place\">Oxford</place-name> " +
                         "    <place-name type=\"dedication\">Christ Church</place-name> " +
                         "    <place-name type=\"county\">Oxfordshire</place-name> " +
                         "    <ring type=\"tower\" tenor=\"31-0-23\" /> " +
                         "  </place> " +
                         "  <date>2016-03-31</date> " +
                         "  <duration>1h00</duration> " +
                         "  <title><changes>1440</changes> " +
                         "    <method>Triton Delight Royal</method>" +
                         "  </title> " +
                         "  <ringers>" +
                         "    <ringer bell=\"1\">Bernard J Stone</ringer> " +
                         "    <ringer bell=\"2\" conductor=\"true\">Robin O Hall</ringer> " +
                         "    <ringer bell=\"3\">Michele Winter</ringer> " +
                         "    <ringer bell=\"4\">Ryan E Mills</ringer> " +
                         "    <ringer bell=\"5\">Stephen M Jones</ringer> " +
                         "    <ringer bell=\"6\">Stuart F Gibson</ringer> " +
                         "    <ringer bell=\"7\">Elizabeth C Frye</ringer> " +
                         "    <ringer bell=\"8\">Michael A Williams</ringer> " +
                         "    <ringer bell=\"9\">Mark D Tarrant</ringer> " +
                         "    <ringer bell=\"10\">Colin M Lee</ringer> " +
                         "  </ringers> " +
                         "  <footnote>Sorry.</footnote> " +
                         "  <timestamp>2016-03-31T21:50:11</timestamp> " +
                         "</performance> " +
                         "</performances>";

        given(bellBoardHttpService.getPerformances()).willReturn(new ByteArrayInputStream(xml.getBytes()));

        Quarter expectedQuarter1 = quarterBuilder()
                                       .date(new SimpleDateFormat("YYYY-MM-dd").parse("2016-04-10"))
                                       .location("Abingdon")
                                       .changes(1280)
                                       .method("Yorkshire Surprise")
                                       .stage("Major")
                                       .ringer("Rebecca Franklin", 1)
                                       .ringer("Brian Read", 2)
                                       .ringer("Susan Read", 3)
                                       .ringer("Sarah Barnes", 4)
                                       .ringer("David Thomas", 5, true)
                                       .ringer("Matthew Franklin", 6)
                                       .ringer("Tim Pett", 7)
                                       .ringer("Ryan Mills", 8)
                                       .build();
        Quarter expectedQuarter2 = quarterBuilder()
                                       .date(new SimpleDateFormat("YYYY-MM-dd").parse("2016-03-31"))
                                       .location("Oxford")
                                       .changes(1440)
                                       .method("Triton Delight")
                                       .stage("Royal")
                                       .ringer("Bernard J Stone", 1)
                                       .ringer("Robin O Hall", 2, true)
                                       .ringer("Michele Winter", 3)
                                       .ringer("Ryan E Mills", 4)
                                       .ringer("Stephen M Jones", 5)
                                       .ringer("Stuart F Gibson", 6)
                                       .ringer("Elizabeth C Frye", 7)
                                       .ringer("Michael A Williams", 8)
                                       .ringer("Mark D Tarrant", 9)
                                       .ringer("Colin M Lee", 10)
                                       .build();

        List<Quarter> expectedQuarters = ImmutableList.<Quarter>builder().add(expectedQuarter1).add(expectedQuarter2)
                                             .build();

        List<Quarter> quarters = _bellBoardService.addPerformances();

        assertThat(quarters, is(expectedQuarters));
    }

    @Test
    public void testAddPerformanceGivenId() throws Exception {
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
                                      .ringer("Christine Mills", 1)
                                      .ringer("Mark R Eccleston", 2, true)
                                      .ringer("Maurice F Edwards", 3)
                                      .ringer("Jonathan P Healy", 4)
                                      .ringer("Richard L Jones", 5)
                                      .ringer("Paul E Bibilo", 6)
                                      .ringer("Michael P A Wilby", 7)
                                      .ringer("Richard B Grimmett", 8)
                                      .build();

        given(bellBoardHttpService.getPerformance(id)).willReturn(new ByteArrayInputStream(xml.getBytes()));

        Quarter quarter = _bellBoardService.addPerformance(id);

        assertThat(quarter, is(expectedQuarter));
    }
}

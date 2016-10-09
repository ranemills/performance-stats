package com.mills.performances.builders;

import com.google.common.collect.ImmutableMap;
import com.mills.bellboard.models.xml.BBPerformance;
import com.mills.bellboard.models.xml.BBPerformanceRinger;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.Location;
import com.mills.performances.models.Performance;
import com.mills.performances.models.Ringer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ryan on 14/04/16.
 */
public class PerformanceBuilder {

    private static final Logger log = Logger.getLogger(PerformanceBuilder.class);

    private Date _date = DateTime.now().toDate();
    private Integer _changes;
    private String _method;
    private String _stage;
    private Location _location;
    private List<Ringer> _ringers;
    private String _bellboardId;
    private BellBoardImport _bellboardImport;
    private Integer _time;

    private PerformanceBuilder() {
        _ringers = new ArrayList<>();
    }

    public static PerformanceBuilder performanceBuilder() {
        return new PerformanceBuilder();
    }

    public static PerformanceBuilder fromBBPeformance(BBPerformance bbPerformance)
    {
        PerformanceBuilder builder = performanceBuilder();

        builder.bellboardId(bbPerformance.getBellboadId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            builder.date(sdf.parse(bbPerformance.getDate()));
        } catch (ParseException e) {
        }

        builder.location(bbPerformance.getPlace().getDedication(),
                         bbPerformance.getPlace().getTown(),
                         bbPerformance.getPlace().getCounty());

        Map<String, String> methodParts = parseMethod(bbPerformance.getTitle().getMethod());
        builder.stage(methodParts.get("stage"));
        builder.method(methodParts.get("method"));

        builder.changes(bbPerformance.getTitle().getChanges());
        for (BBPerformanceRinger ringer : bbPerformance.getRingers()) {
            if (ringer.getConductor()) {
                builder.ringer(ringer.getBell(), ringer.getName(), ringer.getConductor());
            } else {
                builder.ringer(ringer.getBell(), ringer.getName());
            }
        }

        builder.time(parseTime(bbPerformance.getDuration()));

        return builder;
    }

    private static Map<String, String> parseMethod(String methodString) {
        Boolean isBracketedMethodCount = false;

        String bracketedMethodCount = "";

        Pattern bracketedMethodCountPattern = Pattern.compile("\\([[\\d]*\\s?[\\w]*[\\\\/]?]*\\)");
        Matcher m = bracketedMethodCountPattern.matcher(methodString);
        if (m.find()) {
            isBracketedMethodCount = true;
            bracketedMethodCount = normaliseBracketedMethodCount(m.group());
            methodString = m.replaceAll("");
        }

        String[] methodParts = methodString.split(" ");
        String stage = methodParts[methodParts.length - 1];
        String method = StringUtils.join(Arrays.copyOfRange(methodParts, 0, methodParts.length - 1), " ");

        if (isBracketedMethodCount) {
            method = method + " " + bracketedMethodCount;
            StringUtils.join(Arrays.asList(method, bracketedMethodCount), " ");
        }

        return ImmutableMap.<String, String>builder()
                   .put("method", method.replaceFirst("\\A\\s", "").replaceAll("\\s+", " "))
                   .put("stage", stage)
                   .build();
    }

    private static String normaliseBracketedMethodCount(String methodCount) {
        return methodCount.replaceAll("Methods", "m").replaceAll("methods", "m").replaceAll("\\s", "");
    }

    public static PerformanceBuilder tritonDelightPerformance() {
        return performanceBuilder().bellboardId("1500")
                                   .date(new DateTime(2016, 3, 21, 0, 0).toDate())
                                   .location("St Thos", "Oxford", "Oxfordshire")
                                   .changes(1440)
                                   .method("Triton Delight")
                                   .stage("Royal")
                                   .time(55)
                                   .ringer(1, "Bernard J Stone")
                                   .ringer(2, "Robin O Hall", true)
                                   .ringer(3, "Michele Winter")
                                   .ringer(4, "Ryan E Mills")
                                   .ringer(5, "Stephen M Jones")
                                   .ringer(6, "Stuart F Gibson")
                                   .ringer(7, "Elizabeth C Frye")
                                   .ringer(8, "Michael A Williams")
                                   .ringer(9, "Mark D Tarrant")
                                   .ringer(10, "Colin M Lee");
    }

    public static PerformanceBuilder yorkshireMajorPerformance() {
        return performanceBuilder().bellboardId("101")
                                   .date(new DateTime(2016, 4, 10, 0, 0).toDate())
                                   .location("St Helen", "Abingdon", "Oxfordshire")
                                   .changes(1280)
                                   .method("Yorkshire Surprise")
                                   .stage("Major")
                                   .time(45)
                                   .ringer(1, "Rebecca Franklin")
                                   .ringer(2, "Brian Read")
                                   .ringer(3, "Susan Read")
                                   .ringer(4, "Sarah Barnes")
                                   .ringer(5, "David Thomas", true)
                                   .ringer(6, "Matthew Franklin")
                                   .ringer(7, "Tim Pett")
                                   .ringer(8, "Ryan Mills");
    }

    private static Integer parseTime(String time)
    {
        try {
            Integer output = 0;
            time = time.trim();

            String pattern = "(\\d*)";
            Pattern r = Pattern.compile(pattern);

            Matcher m = r.matcher(time);

            if (time.contains("h") || time.contains("H") || time.contains(":")) {
                if (m.find()) {
                    String hours = m.group(0);
                    output += Integer.decode(hours) * 60;
                }
            }

            while (m.find()) {
                if (StringUtils.isNotEmpty(m.group(0))) {
                    output += Integer.valueOf(m.group(0));
                }
            }

            if (output == 0) {
                return null;
            }

            return output;

        } catch (NumberFormatException e) {
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public PerformanceBuilder bellboardImport(BellBoardImport bellboardImport) {
        this._bellboardImport = bellboardImport;
        return this;
    }

    public PerformanceBuilder bellboardId(String bellboardId) {
        this._bellboardId = bellboardId;
        return this;
    }

    public PerformanceBuilder date(Date date) {
        this._date = date;
        return this;
    }

    public PerformanceBuilder changes(int changes) {
        this._changes = changes;
        return this;
    }

    public PerformanceBuilder method(String method) {
        this._method = method;
        return this;
    }

    public PerformanceBuilder fromMethodString(String method) {
        Map<String, String> methodParts = parseMethod(method);
        this._method = methodParts.get("method");
        this._stage = methodParts.get("stage");
        return this;
    }

    public PerformanceBuilder stage(String stage) {
        this._stage = stage;
        return this;
    }

    public PerformanceBuilder location(Location location) {
        this._location = location;
        return this;
    }

    public PerformanceBuilder location(String dedication, String town, String county) {
        Location location = new Location();
        location.setDedication(dedication);
        location.setTown(town);
        location.setCounty(county);
        return location(location);
    }

    public PerformanceBuilder ringer(Ringer ringer) {
        this._ringers.add(ringer);
        return this;
    }

    public PerformanceBuilder ringer(int bell, String name) {
        Ringer ringer = new Ringer(name, bell);
        return ringer(ringer);
    }

    public PerformanceBuilder ringer(int bell, String name, boolean conductor) {
        this._ringers.add(new Ringer(name, bell, conductor));
        return this;
    }

    public PerformanceBuilder time(Integer hours, Integer minutes)
    {
        this._time = hours * 60 + minutes;
        return this;
    }

    public PerformanceBuilder time(Integer minutes)
    {
        this._time = minutes;
        return this;
    }

    public PerformanceBuilder time(String time)
    {
        return time(parseTime(time));
    }

    public Performance build() {
        Performance performance = new Performance();
        performance.setDate(_date);
        performance.setChanges(_changes);
        performance.setMethod(_method);
        performance.setStage(_stage);
        performance.setLocation(_location);
        performance.setRingers(_ringers);
        performance.setBellboardId(_bellboardId);
        performance.setTime(_time);
        performance.setYear(_date.getYear());
        if (_bellboardImport != null) {
            performance.setBellBoardImport(_bellboardImport);
        }

        return performance;
    }

}

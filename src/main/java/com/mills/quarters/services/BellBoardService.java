package com.mills.quarters.services;

import com.google.common.collect.ImmutableMap;
import com.mills.quarters.builders.QuarterBuilder;
import com.mills.quarters.models.BellBoardImport;
import com.mills.quarters.models.Quarter;
import com.mills.quarters.models.xml.BBPerformance;
import com.mills.quarters.models.xml.BBPerformanceList;
import com.mills.quarters.models.xml.BBPerformanceRinger;
import com.mills.quarters.repositories.QuarterRepository;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mills.quarters.builders.QuarterBuilder.quarterBuilder;

@Service
public class BellBoardService {

    private final QuarterRepository quarterRepository;

    private final BellBoardHttpService bellBoardHttpService;

    @Autowired
    public BellBoardService(QuarterRepository quarterRepository, BellBoardHttpService bellBoardHttpService) {
        this.quarterRepository = quarterRepository;
        this.bellBoardHttpService = bellBoardHttpService;
    }

    private static String normaliseBracketedMethodCount(String methodCount) {
        return methodCount.replaceAll("Methods", "m").replaceAll("methods", "m").replaceAll("\\s", "");
    }

    private BBPerformance parseSinglePerformanceXml(InputStream is)
        throws Exception
    {
        BBPerformance bbPerformance;

        String output = IOUtils.toString(is);
        Serializer serializer = new Persister();
        bbPerformance = serializer.read(BBPerformance.class, output);

        return bbPerformance;
    }

    /*
     * Find a performance and add it to the system. Return the Quarter object.
     */
    public Quarter addPerformance(String id)
    {
        try (InputStream is = bellBoardHttpService.getPerformance(id)) {
            BBPerformance performanceXml = parseSinglePerformanceXml(is);
            return addPerformance(performanceXml);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Quarter();
        }
    }

    public List<Quarter> addPerformances()
    {
        BBPerformanceList bbPerformanceList;

        try (InputStream is = bellBoardHttpService.getPerformances()) {
            String output = IOUtils.toString(is);
            Serializer serializer = new Persister();
            bbPerformanceList = serializer.read(BBPerformanceList.class, output, false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            bbPerformanceList = new BBPerformanceList();
        }

        List<Quarter> quarters = new ArrayList<>();

        for (BBPerformance performance : bbPerformanceList.getPerformances()) {
            quarters.add(addPerformance(performance));
        }

        return quarters;
    }

    public List<Quarter> addPerformances(String inUrl)
        throws URISyntaxException
    {
        BBPerformanceList bbPerformanceList;
        String outUrl = inUrl.replace("search.php", "export.php");
        try (InputStream is = bellBoardHttpService.getPerformances(outUrl)) {
            String output = IOUtils.toString(is);
            Serializer serializer = new Persister();
            bbPerformanceList = serializer.read(BBPerformanceList.class, output, false);
        } catch (URISyntaxException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            bbPerformanceList = new BBPerformanceList();
        }

        List<Quarter> quarters = new ArrayList<>();

        for (BBPerformance performance : bbPerformanceList.getPerformances()) {
            quarters.add(addPerformance(performance));
        }

        return quarters;

    }

    private Quarter addPerformance(BBPerformance performance) {
        QuarterBuilder builder = quarterBuilder();

        builder.bellboardId(performance.getBellboadId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            builder.date(sdf.parse(performance.getDate()));
        } catch (ParseException e) {
        }

        builder.location(performance.getPlace().getPlace());

        Map<String, String> methodParts = parseMethod(performance.getTitle().getMethod());
        builder.stage(methodParts.get("stage"));
        builder.method(methodParts.get("method"));

        builder.changes(performance.getTitle().getChanges());
        for (BBPerformanceRinger ringer : performance.getRingers()) {
            if (ringer.getConductor()) {
                builder.ringer(ringer.getBell(), ringer.getName(), ringer.getConductor());
            } else {
                builder.ringer(ringer.getBell(), ringer.getName());
            }
        }

        Quarter quarter = builder.build();

        quarterRepository.save(quarter);

        return quarter;
    }

    private Map<String, String> parseMethod(String methodString) {
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

    public List<Quarter> addPerformances(BellBoardImport bbImport)
        throws URISyntaxException
    {
        return addPerformances(bbImport.getUrl());
    }
}




package com.mills.quarters.services;

import com.mills.quarters.builders.QuarterBuilder;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mills.quarters.builders.QuarterBuilder.quarterBuilder;

@Service
public class BellBoardService {

    QuarterRepository quarterRepository;

    BellBoardHttpService bellBoardHttpService;

    @Autowired
    public BellBoardService(QuarterRepository quarterRepository, BellBoardHttpService bellBoardHttpService) {
        this.quarterRepository = quarterRepository;
        this.bellBoardHttpService = bellBoardHttpService;
    }

    public BBPerformance parseSinglePerformanceXml(InputStream is)
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
        throws URISyntaxException, IOException, ParseException
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
        throws IOException, URISyntaxException, ParseException
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

    private Quarter addPerformance(BBPerformance performance) {
        DateFormat format = new SimpleDateFormat("YYYY-MM-dd");

        QuarterBuilder builder = quarterBuilder();

        try {
            builder.date(format.parse(performance.getDate()));
        } catch (Exception e) {
            System.out.println("Date parse exception");
        }

        builder.location(performance.getPlace().getPlace());
        String[] methodParts = performance.getTitle().getMethod().split(" ");
        builder.stage(methodParts[methodParts.length - 1]);
        builder.method(StringUtils.join(Arrays.copyOfRange(methodParts, 0, methodParts.length - 1), " "));
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
}




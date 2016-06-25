package com.mills.performances.services.impl;

import com.mills.bellboard.models.xml.BBPerformance;
import com.mills.bellboard.models.xml.BBPerformanceRinger;
import com.mills.performances.builders.PerformanceBuilder;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.Performance;
import com.mills.performances.services.BellBoardService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mills.performances.builders.PerformanceBuilder.fromBBPeformance;
import static com.mills.performances.builders.PerformanceBuilder.quarterBuilder;

@Service
public class BellBoardServiceImpl implements BellBoardService {

    private final com.mills.bellboard.services.BellBoardService _bellBoardService;

    public BellBoardServiceImpl() {
        _bellBoardService = new com.mills.bellboard.services.impl.BellBoardServiceImpl();
    }

    @Override
    public List<Performance> getPerformances(BellBoardImport bbImport)
        throws URISyntaxException
    {
        String outUrl = bbImport.getUrl().replace("search.php", "export.php");

        List<Performance> performances = new ArrayList<>();

        try {
            for (BBPerformance bbPerformance : _bellBoardService.getPerformances(outUrl,
                                                                                 new DateTime(bbImport.getLastImport()))) {

                performances.add(fromBBPeformance(bbPerformance).bellboardImport(bbImport).build());
            }
        }
        catch (Exception e)
        {
            // TODO: Deal with this a bit more smoothly. For the time being, just return an empty list.
        }

        return performances;
    }

    @Override
    public Performance getPerformance(String id)
    {
        try
        {
            BBPerformance bbPerformance = _bellBoardService.getSinglePerformance(id);
            return fromBBPeformance(bbPerformance).build();
        }
        catch (Exception e)
        {
            return null;
        }
    }

}




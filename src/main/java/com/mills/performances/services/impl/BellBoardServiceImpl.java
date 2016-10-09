package com.mills.performances.services.impl;

import com.mills.bellboard.models.xml.BBPerformance;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.Performance;
import com.mills.performances.services.BellBoardService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.mills.performances.builders.PerformanceBuilder.fromBBPeformance;

@Service
public class BellBoardServiceImpl implements BellBoardService {

    private com.mills.bellboard.services.BellBoardService _bellBoardService;

    public BellBoardServiceImpl() {
        _bellBoardService = new com.mills.bellboard.services.impl.BellBoardServiceImpl();
    }

    @Override
    public List<Performance> loadPerformances(BellBoardImport bbImport)
    {
        String outUrl = bbImport.getUrl().replace("search.php", "export.php");

        List<Performance> performances = new ArrayList<>();
        DateTime changedSince = bbImport.getLastImport() == null ? null : new DateTime(bbImport.getLastImport());

        try {
            for (BBPerformance bbPerformance : _bellBoardService.getPerformances(outUrl, changedSince)) {
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
    public Performance loadPerformance(String id)
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




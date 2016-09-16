package com.mills.performances.services.impl;

import com.mills.bellboard.models.xml.BBPerformance;
import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.MilestoneFacet;
import com.mills.performances.models.Performance;
import com.mills.performances.repositories.MilestoneFacetRepository;
import com.mills.performances.services.BellBoardService;
import com.mills.performances.services.MilestoneService;
import com.mills.performances.services.PerformanceService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mills.performances.builders.MilestoneFacetBuilder.milestoneFacetBuilder;
import static com.mills.performances.builders.PerformanceBuilder.fromBBPeformance;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    @Autowired
    private MilestoneFacetRepository _milestoneFacetRepository;

    @Autowired
    private PerformanceService _performanceService;

    @Override
    public void updateMilestones(List<Performance> performances) {
        List<MilestoneFacet> facets = _milestoneFacetRepository.findAll();
        for(Performance performance : performances) {
            for (MilestoneFacet facet : facets) {
                boolean match = true;
                for(Map.Entry<PerformanceProperty, Object> propertyObjectEntry : facet.getProperties().entrySet()) {
                    match = _performanceService.propertiesMatch(performance, propertyObjectEntry.getKey(), propertyObjectEntry.getValue());
                    if(!match)
                    {
                        break;
                    }
                }
                if (match) { facet.incrementCount(); }
            }
        }

        _milestoneFacetRepository.save(facets);
    }

    @Override
    public void createInitialMilestoneFacets() {
        MilestoneFacet initialFacet = milestoneFacetBuilder().build();
        _milestoneFacetRepository.save(initialFacet);
    }
}




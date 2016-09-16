package com.mills.performances.services.impl;

import com.mills.performances.enums.MilestoneValue;
import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.MilestoneFacet;
import com.mills.performances.models.Performance;
import com.mills.performances.repositories.MilestoneFacetRepository;
import com.mills.performances.services.MilestoneService;
import com.mills.performances.services.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.mills.performances.builders.MilestoneFacetBuilder.milestoneFacetBuilder;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    @Autowired
    private MilestoneFacetRepository _milestoneFacetRepository;

    @Autowired
    private PerformanceService _performanceService;

    @Override
    public void updateMilestones(List<Performance> performances) {
        List<MilestoneFacet> facets = _milestoneFacetRepository.findAll();

        performances.sort((lhs, rhs) -> lhs.getDate().after(rhs.getDate()) ? 1 : -1);

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
                if (match) {
                    incrementCount(facet, performance, false);
                }
            }
        }

        _milestoneFacetRepository.save(facets);
    }

    @Override
    public void createInitialMilestoneFacets(BellBoardImport bellBoardImport) {
        MilestoneFacet initialFacet = milestoneFacetBuilder(bellBoardImport).build();
        _milestoneFacetRepository.save(initialFacet);
    }

    @Override
    public void incrementCount(MilestoneFacet milestoneFacet, Performance performance, Boolean save) {
        milestoneFacet.incrementCount();
        MilestoneValue milestoneValue = MilestoneValue.fromInt(milestoneFacet.getCount());

        if(milestoneValue != MilestoneValue.NONE) {
            milestoneFacet.addMilestone(milestoneValue, performance);
        }

        if(save) {
            _milestoneFacetRepository.save(milestoneFacet);
        }
    }
}




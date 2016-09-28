package com.mills.performances.services.impl;

import com.mills.performances.enums.MilestoneFacetType;
import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.MilestoneFacet;
import com.mills.performances.models.Performance;
import com.mills.performances.repositories.MilestoneFacetRepository;
import com.mills.performances.repositories.MilestoneRepository;
import com.mills.performances.services.MilestoneService;
import com.mills.performances.services.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.mills.performances.builders.MilestoneFacetBuilder.milestoneFacetBuilder;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    @Autowired
    private MilestoneFacetRepository _milestoneFacetRepository;

    @Autowired
    private MilestoneRepository _milestoneRepository;

    @Autowired
    private PerformanceService _performanceService;

    @Override
    public MilestoneFacet initialiseMilestoneFacet(MilestoneFacet facet) {
        List<Performance> performances = _performanceService.findByProperties(facet.getProperties(), new Sort(Sort.Direction.ASC, "date"));
        for(Performance performance : performances) {
            incrementCount(facet, performance, false);
        }
        _milestoneRepository.save(facet.getMilestones());
        return _milestoneFacetRepository.save(facet);
    }

    @Override
    public void updateMilestones(List<Performance> performances) {
        List<MilestoneFacet> facets = _milestoneFacetRepository.findAll();

        performances.sort((lhs, rhs) -> lhs.getDate().after(rhs.getDate()) ? 1 : -1);

        for (MilestoneFacet facet : facets) {
            for(Performance performance : performances) {
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
            _milestoneRepository.save(facet.getMilestones());
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
        if(milestoneFacet.getType().equals(MilestoneFacetType.COUNT)) {
            milestoneFacet.addValue(1);
            if (isMilestoneCount(milestoneFacet.getValue())) {
                milestoneFacet.addMilestone(milestoneFacet.getValue(), performance);
            }
        }
        else if(milestoneFacet.getType().equals(MilestoneFacetType.DURATION)){
            milestoneFacet.addValue(performance.getTime());
            if(isMilestoneDuration(milestoneFacet.getValue())) {
                milestoneFacet.addMilestone(milestoneFacet.getValue(), performance);
            }
        }

        if(save) {
            _milestoneFacetRepository.save(milestoneFacet);
        }
    }

    @Override
    public Boolean isMilestoneCount(Integer value) {
        return Arrays.asList(1, 5, 10, 25).contains(value) || value % 50 == 0;
    }

    @Override
    public Boolean isMilestoneDuration(Integer value) {
        return Arrays.asList(hours(5), hours(10), hours(25)).contains(value) || value % hours(50) == 0;
    }

    private Integer hours(Integer hours) {
        return hours*60;
    }
}




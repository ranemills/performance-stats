package com.mills.performances.controllers;

import com.mills.performances.builders.MilestoneFacetBuilder;
import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.Milestone;
import com.mills.performances.models.MilestoneFacet;
import com.mills.performances.models.Performance;
import com.mills.performances.repositories.BellBoardImportRepository;
import com.mills.performances.repositories.MilestoneFacetRepository;
import com.mills.performances.repositories.MilestoneRepository;
import com.mills.performances.services.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by ryan on 10/04/16.
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("api/facets")
public class FacetsController {

    @Autowired
    private MilestoneService _milestoneService;
    @Autowired
    private MilestoneFacetRepository _milestoneFacetRepository;

    @Autowired
    private BellBoardImportRepository _bellboardImportRepository;

    @RequestMapping(method = RequestMethod.POST)
    MilestoneFacet newFacet(@RequestBody Map<String, Object> allRequestParams)
        throws Exception
    {
        BellBoardImport bbImport = _bellboardImportRepository.findAll().get(0);
        MilestoneFacetBuilder facetBuilder = MilestoneFacetBuilder.milestoneFacetBuilder(bbImport);
        for(Map.Entry<String, String> entry : ((Map<String, String>) allRequestParams.get("properties")).entrySet()) {
            facetBuilder.addPropertyValue(PerformanceProperty.fromString(entry.getKey()), entry.getValue());
        }
        return _milestoneService.initialiseMilestoneFacet(facetBuilder.build());
    }

    @RequestMapping
    List<MilestoneFacet> getFacets()
        throws Exception
    {
        return _milestoneFacetRepository.findAll();
    }

    @RequestMapping("/properties")
    List<PerformanceProperty> getProperties() {
        return Arrays.asList(PerformanceProperty.values());
    }

}

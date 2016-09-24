package com.mills.performances.controllers;

import com.google.common.collect.ImmutableMap;
import com.mills.performances.builders.MilestoneFacetBuilder;
import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.Milestone;
import com.mills.performances.models.MilestoneFacet;
import com.mills.performances.models.temp.DateTempCount;
import com.mills.performances.models.temp.IntegerTempCount;
import com.mills.performances.models.temp.PerformanceSearchOptions;
import com.mills.performances.models.temp.StringTempCount;
import com.mills.performances.models.temp.TempCount;
import com.mills.performances.repositories.BellBoardImportRepository;
import com.mills.performances.repositories.MilestoneRepository;
import com.mills.performances.repositories.PerformanceRepository;
import com.mills.performances.services.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mills.performances.models.temp.PerformanceSearchOptions.searchOptions;

/**
 * Created by ryan on 10/04/16.
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("api/milestones")
public class MilestonesController {

    @Autowired
    private MilestoneService _milestoneService;
    @Autowired
    private MilestoneRepository _milestoneRepository;

    @Autowired
    private BellBoardImportRepository _bellboardImportRepository;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    MilestoneFacet getAvailableFilters(@RequestBody Map<String, String> allRequestParams)
        throws Exception
    {
        BellBoardImport bbImport = _bellboardImportRepository.findAll().get(0);
        MilestoneFacetBuilder facetBuilder = MilestoneFacetBuilder.milestoneFacetBuilder(bbImport);
        for(Map.Entry<String, String> entry : allRequestParams.entrySet()) {
            facetBuilder.addPropertyValue(PerformanceProperty.fromString(entry.getKey()), entry.getValue());
        }
        return _milestoneService.initialiseMilestoneFacet(facetBuilder.build());
    }

    @RequestMapping
    List<Milestone> getMilestones()
        throws Exception
    {
        return _milestoneRepository.findAll(new Sort(Sort.Direction.DESC, "date"));
    }

}

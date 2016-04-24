package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableMap;
import com.mills.quarters.daos.QuarterDao;
import com.mills.quarters.models.temp.TempCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mills.quarters.daos.QuarterDao.SearchOptions.searchOptions;

/**
 * Created by ryan on 10/04/16.
 */
@RestController
@RequestMapping("api/stats")
public class StatsController {

    @Autowired
    QuarterDao quarterDao;

    @RequestMapping("/available")
    Map<String, String> getAvailableFilters(@RequestParam Map<String, String> allRequestParams) {
        return ImmutableMap.<String, String>builder()
                   .put("method", "Methods")
                   .put("stage", "Stages")
                   .put("ringer", "Ringers")
                   .build();
    }

    @RequestMapping("/methods")
    List<TempCount> getMethods(@RequestParam Map<String, String> allRequestParams) {
        return quarterDao.findMethodCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/stages")
    List<TempCount> getStages(@RequestParam Map<String, String> allRequestParams) {
        return quarterDao.findStageCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/ringers")
    List<TempCount> getRingers(@RequestParam Map<String, String> allRequestParams) {
        return quarterDao.findRingerCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/filters")
    Map<String, List<TempCount>> getFilters(@RequestParam Map<String, String> allRequestParams) {
        List<TempCount> ringers = quarterDao.findRingerCounts(searchOptions(allRequestParams));
        List<TempCount> stages = quarterDao.findStageCounts(searchOptions(allRequestParams));
        List<TempCount> methods = quarterDao.findMethodCounts(searchOptions(allRequestParams));

        Map<String, List<TempCount>> filters = new HashMap<>();
        filters.put("method", methods);
        filters.put("stage", stages);
        filters.put("ringer", ringers);
        return filters;
    }

}

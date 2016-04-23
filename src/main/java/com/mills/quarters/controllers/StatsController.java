package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableMap;
import com.mills.quarters.daos.QuarterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
    Map<String, Integer> getMethods(@RequestParam Map<String, String> allRequestParams) {
        return quarterDao.findMethodCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/stages")
    Map<String, Integer> getStages(@RequestParam Map<String, String> allRequestParams) {
        return quarterDao.findStageCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/ringers")
    Map<String, Integer> getRingers(@RequestParam Map<String, String> allRequestParams) {
        return quarterDao.findRingerCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/filters")
    Map<String, Map<String, Integer>> getFilters(@RequestParam Map<String, String> allRequestParams) {
        Map<String, Integer> ringers = quarterDao.findRingerCounts(searchOptions(allRequestParams));
        Map<String, Integer> stages = quarterDao.findStageCounts(searchOptions(allRequestParams));
        Map<String, Integer> methods = quarterDao.findMethodCounts(searchOptions(allRequestParams));

        Map<String, Map<String, Integer>> filters = new HashMap<>();
        filters.put("method", methods);
        filters.put("stage", stages);
        filters.put("ringer", ringers);
        return filters;
    }

}

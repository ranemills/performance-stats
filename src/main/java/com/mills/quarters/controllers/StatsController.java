package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableMap;
import com.mills.quarters.daos.QuarterDao;
import com.mills.quarters.models.temp.MethodCount;
import com.mills.quarters.models.temp.RingerCount;
import com.mills.quarters.models.temp.StageCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.mills.quarters.daos.QuarterDao.SearchOptions.searchOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ryan on 10/04/16.
 */
@RestController
@RequestMapping("api/stats")
public class StatsController {

    @Autowired
    QuarterDao quarterDao;

    @RequestMapping("/methods")
    List<MethodCount> getMethods(@RequestParam Map<String,String> allRequestParams) {
        return quarterDao.findMethodCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/stages")
    List<StageCount> getStages(@RequestParam Map<String,String> allRequestParams) {
        return quarterDao.findStageCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/ringers")
    List<RingerCount> getRingers(@RequestParam Map<String,String> allRequestParams) {
        return quarterDao.findRingerCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/filters")
    Map<String, List<?>> getFilters(@RequestParam Map<String,String> allRequestParams) {
        List<RingerCount> ringers = quarterDao.findRingerCounts(searchOptions(allRequestParams));
        List<StageCount> stages = quarterDao.findStageCounts(searchOptions(allRequestParams));
        List<MethodCount> methods = quarterDao.findMethodCounts(searchOptions(allRequestParams));

        Map<String, List<?>> filters = new HashMap<String, List<?>>();
        filters.put("methods", methods);
        filters.put("stages", stages);
        filters.put("ringers", ringers);
        return filters;
    }

}

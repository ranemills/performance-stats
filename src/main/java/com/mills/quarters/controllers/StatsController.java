package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableMap;
import com.mills.quarters.daos.QuarterDao;
import com.mills.quarters.models.temp.DateTempCount;
import com.mills.quarters.models.temp.StringTempCount;
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
@SuppressWarnings("unused")
@RestController
@RequestMapping("api/stats")
public class StatsController {

    @Autowired
    QuarterDao quarterDao;

    @RequestMapping("/available")
    Map<String, String> getAvailableFilters(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return ImmutableMap.<String, String>builder()
                   .put("method", "Methods")
                   .put("stage", "Stages")
                   .put("ringer", "Ringers")
                   .put("date", "Dates")
                   .build();
    }

    @RequestMapping("/methods")
    List<StringTempCount> getMethods(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return quarterDao.findMethodCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/stages")
    List<StringTempCount> getStages(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return quarterDao.findStageCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/ringers")
    List<StringTempCount> getRingers(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return quarterDao.findRingerCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/filters")
    Map<String, List<? extends TempCount>> getFilters(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        QuarterDao.SearchOptions searchOptions = searchOptions(allRequestParams);

        List<StringTempCount> ringers = quarterDao.findRingerCounts(searchOptions);
        List<StringTempCount> stages = quarterDao.findStageCounts(searchOptions);
        List<StringTempCount> methods = quarterDao.findMethodCounts(searchOptions);
        List<DateTempCount> dates = quarterDao.findDateCounts(searchOptions);

        Map<String, List<? extends TempCount>> filters = new HashMap<>();
        filters.put("method", methods);
        filters.put("stage", stages);
        filters.put("ringer", ringers);
        filters.put("date", dates);
        return filters;
    }

}

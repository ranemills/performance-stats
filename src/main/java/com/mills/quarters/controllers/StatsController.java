package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableMap;
import com.mills.quarters.models.temp.DateTempCount;
import com.mills.quarters.models.temp.QuarterSearchOptions;
import com.mills.quarters.models.temp.StringTempCount;
import com.mills.quarters.models.temp.TempCount;
import com.mills.quarters.repositories.QuarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mills.quarters.models.temp.QuarterSearchOptions.searchOptions;

/**
 * Created by ryan on 10/04/16.
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("api/stats")
public class StatsController {

    @Autowired
    private QuarterRepository _quarterRepository;

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
        return _quarterRepository.findMethodCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/stages")
    List<StringTempCount> getStages(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return _quarterRepository.findStageCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/ringers")
    List<StringTempCount> getRingers(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return _quarterRepository.findRingerCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/filters")
    Map<String, List<? extends TempCount>> getFilters(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        QuarterSearchOptions searchOptions = searchOptions(allRequestParams);

        List<StringTempCount> ringers = _quarterRepository.findRingerCounts(searchOptions);
        List<StringTempCount> stages = _quarterRepository.findStageCounts(searchOptions);
        List<StringTempCount> methods = _quarterRepository.findMethodCounts(searchOptions);
        List<DateTempCount> dates = _quarterRepository.findDateCounts(searchOptions);

        Map<String, List<? extends TempCount>> filters = new HashMap<>();
        filters.put("method", methods);
        filters.put("stage", stages);
        filters.put("ringer", ringers);
        filters.put("date", dates);
        return filters;
    }

}

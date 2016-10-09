package com.mills.performances.controllers;

import com.google.common.collect.ImmutableMap;
import com.mills.performances.models.temp.DateTempCount;
import com.mills.performances.models.temp.IntegerTempCount;
import com.mills.performances.models.temp.LocationTempCount;
import com.mills.performances.models.temp.PerformanceSearchOptions;
import com.mills.performances.models.temp.StringTempCount;
import com.mills.performances.models.temp.TempCount;
import com.mills.performances.repositories.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("api/stats")
public class StatsController {

    @Autowired
    private PerformanceRepository _performanceRepository;

    @RequestMapping("/snapshot")
    Map<String, Integer> getSnapshot()
        throws Exception
    {
        PerformanceSearchOptions emptyOptions = new PerformanceSearchOptions();

        int totalDuration = 0;
        for(IntegerTempCount durationEntry : _performanceRepository.findDurationCounts(emptyOptions)) {
            totalDuration += durationEntry.getProperty();
        }

        return ImmutableMap.<String, Integer>builder().put("total", (int) _performanceRepository.count())
                                                      .put("ringers", _performanceRepository.findRingerCounts(emptyOptions).size())
                                                      .put("methods", _performanceRepository.findMethodCounts(emptyOptions).size())
                                                      .put("towers", _performanceRepository.findLocationCounts(emptyOptions).size())
                                                      .put("time", totalDuration)
                                                      .build();
    }

    @RequestMapping("/available")
    Map<String, String> getAvailableFilters(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return ImmutableMap.<String, String>builder()
                   .put("method", "Methods")
                   .put("stage", "Stages")
                   .put("ringer", "Ringers")
                   .put("date", "Dates")
                   .put("year", "Years")
                   .build();
    }

    @RequestMapping("/methods")
    List<StringTempCount> getMethods(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return _performanceRepository.findMethodCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/stages")
    List<StringTempCount> getStages(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return _performanceRepository.findStageCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/ringers")
    List<StringTempCount> getRingers(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return _performanceRepository.findRingerCounts(searchOptions(allRequestParams));
    }

    @RequestMapping("/locations")
    List<LocationTempCount> getLocations(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return _performanceRepository.findLocationCounts(searchOptions(allRequestParams));
    }

    @Deprecated
    @RequestMapping("/filters")
    Map<String, List<? extends TempCount>> getFilters(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        PerformanceSearchOptions searchOptions = searchOptions(allRequestParams);

        List<StringTempCount> ringers = _performanceRepository.findRingerCounts(searchOptions);
        List<StringTempCount> stages = _performanceRepository.findStageCounts(searchOptions);
        List<StringTempCount> methods = _performanceRepository.findMethodCounts(searchOptions);
        List<DateTempCount> dates = _performanceRepository.findDateCounts(searchOptions);
        List<IntegerTempCount> years = _performanceRepository.findYearCounts(searchOptions);
        List<IntegerTempCount> times = _performanceRepository.findDurationCounts(searchOptions);
        List<LocationTempCount> locations = _performanceRepository.findLocationCounts(searchOptions);

        Map<String, List<? extends TempCount>> filters = new HashMap<>();
        filters.put("method", methods);
        filters.put("stage", stages);
        filters.put("ringer", ringers);
        filters.put("date", dates);
        filters.put("year", years);
        filters.put("time", times);
        filters.put("location", locations);
        return filters;
    }

}

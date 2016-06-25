package com.mills.performances.controllers;

import com.mills.performances.models.Performance;
import com.mills.performances.repositories.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.mills.performances.models.temp.PerformanceSearchOptions.searchOptions;

/**
 * Created by ryan on 10/04/16.
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/performances")
public class QuartersController {

    @Autowired
    private PerformanceRepository _performanceRepository;

    @RequestMapping("/list")
    List<Performance> getQuarters(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return _performanceRepository.findPerformances(searchOptions(allRequestParams));
    }

    //TODO: Add this method
//    @RequestMapping(value = "/add", method = RequestMethod.PUT)
//    void addData() {
//    }

}

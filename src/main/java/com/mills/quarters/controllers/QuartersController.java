package com.mills.quarters.controllers;

import com.mills.quarters.models.Quarter;
import com.mills.quarters.repositories.QuarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.mills.quarters.models.temp.QuarterSearchOptions.searchOptions;

/**
 * Created by ryan on 10/04/16.
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/quarters")
public class QuartersController {

    @Autowired
    private QuarterRepository _quarterRepository;

    @RequestMapping("/list")
    List<Quarter> getQuarters(@RequestParam Map<String, String> allRequestParams)
        throws Exception
    {
        return _quarterRepository.findQuarters(searchOptions(allRequestParams));
    }

    //TODO: Add this method
//    @RequestMapping(value = "/add", method = RequestMethod.PUT)
//    void addData() {
//    }

}

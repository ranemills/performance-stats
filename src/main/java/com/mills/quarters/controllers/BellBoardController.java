package com.mills.quarters.controllers;

import com.mills.quarters.models.Quarter;
import com.mills.quarters.services.BellBoardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by ryan on 23/04/16.
 */
@RequestMapping("/api/bellboard")
@RestController
public class BellBoardController {

    @Autowired
    BellBoardService _bellBoardService;

    @RequestMapping("/add/{id}")
    public Quarter addPerformance(@PathVariable("id") String id) throws
        URISyntaxException, IOException, ParseException
    {
        return _bellBoardService.addPerformance(id);
    }

    @RequestMapping("/add")
    public List<Quarter> addPerformances() throws
        URISyntaxException, IOException, ParseException
    {
        return _bellBoardService.addPerformances();
    }
}

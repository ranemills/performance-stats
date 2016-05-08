package com.mills.quarters.controllers;

import com.mills.quarters.models.Quarter;
import com.mills.quarters.services.BellBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 23/04/16.
 */
@SuppressWarnings("unused")
@RequestMapping("/api/bellboard")
@RestController
public class BellBoardController {

    @Autowired
    private BellBoardService _bellBoardService;

    @RequestMapping("/add/{id}")
    public Quarter addPerformance(@PathVariable("id") String id)
    {
        return _bellBoardService.addPerformance(id);
    }

    @RequestMapping("/add")
    public List<Quarter> addPerformances()
    {
        return _bellBoardService.addPerformances();
    }

    @RequestMapping("/import")
    public List<Quarter> importPerformances(@RequestParam String bbUrl)
    {
        try {
            return _bellBoardService.addPerformances(bbUrl);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL");
        }
    }
}

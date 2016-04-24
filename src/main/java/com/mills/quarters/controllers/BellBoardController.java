package com.mills.quarters.controllers;

import com.mills.quarters.models.Quarter;
import com.mills.quarters.services.BellBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ryan on 23/04/16.
 */
@SuppressWarnings("unused")
@RequestMapping("/api/bellboard")
@RestController
public class BellBoardController {

    @Autowired
    BellBoardService _bellBoardService;

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
}

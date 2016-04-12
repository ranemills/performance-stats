package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableMap;
import com.mills.quarters.daos.QuarterDao;
import com.mills.quarters.models.temp.MethodCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by ryan on 10/04/16.
 */
@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    QuarterDao quarterDao;

    @RequestMapping("/methods")
    List<MethodCount> getMethods() {
        return quarterDao.findMethodCounts();
    }

}

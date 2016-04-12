package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by ryan on 10/04/16.
 */
@RestController
@RequestMapping("/stats")
public class StatsController {

    @RequestMapping("/methods")
    Map<String, Integer> getMethods() {
        ImmutableMap.Builder<String, Integer> map = ImmutableMap.<String, Integer>builder();
        map.put("Plain Bob", 15);
        map.put("Grandsire", 20);
        return map.build();
    }

}

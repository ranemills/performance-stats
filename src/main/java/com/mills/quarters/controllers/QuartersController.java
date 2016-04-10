package com.mills.quarters.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ryan on 10/04/16.
 */
@RestController
@RequestMapping("/quarters")
public class QuartersController {

    @RequestMapping("/list")
    String getQuarters() {
        return "hello";
    }


}

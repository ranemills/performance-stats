package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableList;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ryan on 10/04/16.
 */
@RestController
@RequestMapping("/quarters")
public class QuartersController {

    @RequestMapping("/list")
    List<String> getQuarters() {
        return ImmutableList.<String>builder().add("hello").build();
    }

}

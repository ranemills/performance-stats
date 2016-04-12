package com.mills.quarters.controllers;

import com.mills.quarters.models.Quarter;
import com.mills.quarters.repositories.QuarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ryan on 10/04/16.
 */
@RestController
@RequestMapping("/quarters")
public class QuartersController {

    @Autowired
    private QuarterRepository quarterRepository;

    @RequestMapping("/list")
    List<Quarter> getQuarters() {
        return quarterRepository.findAll();
    }

}

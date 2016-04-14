package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableList;
import com.mills.quarters.models.Quarter;
import com.mills.quarters.repositories.QuarterRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static com.mills.quarters.builders.QuarterBuilder.quarterBuilder;

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

    @RequestMapping("/add")
    void addData() {
        quarterRepository.save(ImmutableList.<Quarter>builder()
                                   .add(quarterBuilder().changes(5056)
                                            .method("Cambridge")
                                            .stage("Major")
                                            .ringer("Ryan Mills", 1)
                                            .ringer("Lydia", 2)
                                            .build())
                                   .add(quarterBuilder().changes(1296)
                                            .method("Cambridge")
                                            .stage("Minor")
                                            .ringer("Ryan Mills", 1)
                                            .ringer("Lydia", 2)
                                            .build())
                                   .add(quarterBuilder()
                                            .ringer("Ryan Mills", 1)
                                            .ringer("Claire", 2)
                                            .changes(1280)
                                            .method("Yorkshire")
                                            .stage("Major")
                                            .build())
                                   .build());
    }

}

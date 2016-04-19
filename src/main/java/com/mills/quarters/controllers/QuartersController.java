package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableList;
import com.mills.quarters.daos.QuarterDao;
import com.mills.quarters.models.Quarter;
import com.mills.quarters.repositories.QuarterRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.mills.quarters.builders.QuarterBuilder.quarterBuilder;
import static com.mills.quarters.daos.QuarterDao.SearchOptions.searchOptions;

/**
 * Created by ryan on 10/04/16.
 */
@RestController
@RequestMapping("/api/quarters")
public class QuartersController {

    @Autowired
    private QuarterRepository quarterRepository;
    @Autowired
    private QuarterDao quarterDao;

    @RequestMapping("/list")
    List<Quarter> getQuarters(@RequestParam Map<String, String> allRequestParams) {
        return quarterDao.findQuarters(searchOptions(allRequestParams));
    }

    @RequestMapping("/add")
    void addData() {
        Random random = new Random();

        long start = 1293861599000L;

        quarterRepository.save(ImmutableList.<Quarter>builder()
                                   .add(quarterBuilder()
                                            .date(new Date((long) (start+random.nextDouble()*60*60*24*365*1000)))
                                            .changes(5042)
                                            .method("Cambridge Surprise")
                                            .stage("Maximus")
                                            .ringer("Bernard J Stone", 1)
                                            .ringer("Harriet J M A Armitage", 2)
                                            .ringer("Colin M Turner", 3)
                                            .ringer("Christopher I Griggs", 4)
                                            .ringer("Simon A Bond", 5)
                                            .ringer("Joanna E Knight", 6)
                                            .ringer("Simon L Edwards", 7)
                                            .ringer("Matthew R Johnson", 8)
                                            .ringer("Stuart F Gibson", 9, true)
                                            .ringer("Ryan Mills", 10)
                                            .ringer("Jonathan Cresshull", 11)
                                            .ringer("Robin O Hall", 12)
                                            .build())
                                   .add(quarterBuilder()
                                            .date(new Date((long) (start+random.nextDouble()*60*60*24*365*1000)))
                                            .changes(5069)
                                            .method("Stedman")
                                            .stage("Caters")
                                            .ringer("Stephanie J Pattenden", 1)
                                            .ringer("Michael Uphill", 2)
                                            .ringer("Thomas J Hinks", 3, true)
                                            .ringer("Harriet J M A Armitage", 4)
                                            .ringer("Ryan E Mills", 5)
                                            .ringer("James R A Dann", 6)
                                            .ringer("James W Belshaw", 7)
                                            .ringer("Ian G Mills", 8)
                                            .ringer("P Quentin Armitage", 9)
                                            .ringer("Richard M Trueman", 10)
                                            .build())
                                   .add(quarterBuilder()
                                            .date(new Date((long) (start+random.nextDouble()*60*60*24*365*1000)))
                                            .changes(1280)
                                            .method("Yorkshire Surprise")
                                            .stage("Major")
                                            .ringer("Rebecca Franklin", 1)
                                            .ringer("Brian Read", 2)
                                            .ringer("Susan Read", 3)
                                            .ringer("Sarah Barnes", 4)
                                            .ringer("David Thomas", 5, true)
                                            .ringer("Matthew Franklin", 6)
                                            .ringer("Tim Pett", 7)
                                            .ringer("Ryan Mills", 8)
                                            .build())
                                   .build());
    }

}

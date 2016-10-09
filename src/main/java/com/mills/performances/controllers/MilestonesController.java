package com.mills.performances.controllers;

import com.mills.performances.models.Milestone;
import com.mills.performances.repositories.BellBoardImportRepository;
import com.mills.performances.repositories.MilestoneRepository;
import com.mills.performances.services.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ryan on 10/04/16.
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("api/milestones")
public class MilestonesController {

    @Autowired
    private MilestoneService _milestoneService;
    @Autowired
    private MilestoneRepository _milestoneRepository;

    @Autowired
    private BellBoardImportRepository _bellboardImportRepository;

    @RequestMapping
    public List<Milestone> getMilestones()
        throws Exception
    {
        return _milestoneRepository.findAll(new Sort(Sort.Direction.DESC, "date"));
    }

}

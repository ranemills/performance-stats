package com.mills.performances.services;

import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.MilestoneFacet;
import com.mills.performances.models.Performance;

import java.util.List;

/**
 * Created by ryan on 25/06/16.
 */
public interface MilestoneService {

    MilestoneFacet initialiseMilestoneFacet(MilestoneFacet facet);

    void updateMilestones(List<Performance> performances);

    void createInitialMilestoneFacets(BellBoardImport bellBoardImport);

    void incrementCount(MilestoneFacet milestoneFacet, Performance performance, Boolean save);

    Boolean isMilestoneCount(Integer value);

    Boolean isMilestoneDuration(Integer value);
}

package com.mills.performances.services;

import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.Performance;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by ryan on 25/06/16.
 */
public interface BellBoardService {

    List<Performance> getPerformances(BellBoardImport bbImport)
        throws URISyntaxException;

    Performance getPerformance(String id);
}

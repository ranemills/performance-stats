package com.mills.quarters.services;

import com.mills.quarters.models.BellBoardImport;
import com.mills.quarters.models.Quarter;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by ryan on 25/06/16.
 */
public interface BellBoardService {
    List<Quarter> addPerformances(BellBoardImport bbImport)
        throws URISyntaxException;

    Quarter quarterFromBBPerformance(String id);
}

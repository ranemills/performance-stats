package com.mills.bellboard.services;

import com.mills.bellboard.models.xml.BBPerformance;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by ryan on 25/06/16.
 */
public interface BellBoardService {
    BBPerformance getSinglePerformance(String id)
        throws Exception;

    List<BBPerformance> getPerformances(String rawUrl, DateTime changedSince)
        throws Exception;
}

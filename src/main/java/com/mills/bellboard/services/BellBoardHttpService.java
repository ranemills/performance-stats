package com.mills.bellboard.services;

import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Created by ryan on 25/06/16.
 */
public interface BellBoardHttpService {
    InputStream getPerformance(String id)
        throws URISyntaxException, IOException;

    InputStream getPerformances(String rawUrl, DateTime changedSince)
        throws URISyntaxException, IOException;
}

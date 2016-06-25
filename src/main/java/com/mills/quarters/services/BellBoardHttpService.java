package com.mills.quarters.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * Created by ryan on 25/06/16.
 */
public interface BellBoardHttpService {
    InputStream getPerformance(String id)
        throws URISyntaxException, IOException;

    InputStream getPerformances(String rawUrl, Date changedSince)
        throws URISyntaxException, IOException;
}

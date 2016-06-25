package com.mills.bellboard.services.impl;

import com.mills.bellboard.models.xml.BBPerformance;
import com.mills.bellboard.models.xml.BBPerformanceList;
import com.mills.bellboard.services.BellBoardHttpService;
import com.mills.bellboard.services.BellBoardService;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.util.List;

/**
 * Created by ryan on 25/06/16.
 */
public class BellBoardServiceImpl implements BellBoardService{

    private BellBoardHttpService _bellBoardHttpService;

    public BellBoardServiceImpl()
    {
        _bellBoardHttpService = new BellBoardHttpServiceImpl();
    }

    @Override
    public BBPerformance getSinglePerformance(String id)
        throws Exception
    {
        try (InputStream is = _bellBoardHttpService.getPerformance(id)) {
            String output = IOUtils.toString(is);
            Serializer serializer = new Persister();
            return serializer.read(BBPerformance.class, output);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<BBPerformance> getPerformances(String rawUrl, DateTime changedSince)
        throws Exception
    {
        try (InputStream is = _bellBoardHttpService.getPerformances(rawUrl, changedSince)) {
            String output = IOUtils.toString(is);
            Serializer serializer = new Persister();
            BBPerformanceList bbPerformanceList = serializer.read(BBPerformanceList.class, output, false);
            return bbPerformanceList.getPerformances();
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}

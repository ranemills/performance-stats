package com.mills.bellboard.services.impl;

import com.mills.bellboard.services.BellBoardHttpService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by ryan on 23/04/16.
 */
@Service
public class BellBoardHttpServiceImpl implements BellBoardHttpService {

    private InputStream xmlHttpRequest(URI uri)
        throws IOException
    {
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("accept", "application/xml");

        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(httpGet);

        return httpResponse.getEntity().getContent();
    }

    @Override
    public InputStream getPerformance(String id)
        throws URISyntaxException, IOException
    {
        URI uri = new URIBuilder().setScheme("http")
                                  .setHost("bb.ringingworld.co.uk")
                                  .setPath("/view.php")
                                  .setParameter("id", id)
                                  .build();
        return xmlHttpRequest(uri);
    }

    @Override
    public InputStream getPerformances(String rawUrl, DateTime changedSince)
        throws URISyntaxException, IOException
    {
        URI uri;
        if (changedSince != null) {
            uri = new URIBuilder(rawUrl).addParameter("changed_since", changedSince.toString("YYYY-MM-dd"))
                                        .build();
        } else {
            uri = new URI(rawUrl);
        }
        return xmlHttpRequest(uri);
    }

}

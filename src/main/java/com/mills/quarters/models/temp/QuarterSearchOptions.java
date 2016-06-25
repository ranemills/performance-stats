package com.mills.quarters.models.temp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by ryan on 25/06/16.
 */
public class QuarterSearchOptions {

    private String stage;
    private String method;
    private String ringer;
    private Date date;

    private QuarterSearchOptions() {
    }

    public static QuarterSearchOptions searchOptions(Map<String, String> requestParams)
        throws ParseException
    {
        QuarterSearchOptions searchOptions = new QuarterSearchOptions().stage(requestParams.get("stage"))
                                                                       .method(requestParams.get("method"))
                                                                       .ringer(requestParams.get("ringer"));

        if (requestParams.get("date") != null) {
            searchOptions.date(requestParams.get("date"));
        }
        return searchOptions;
    }

    public Date getDate() {
        return date;
    }

    public String getStage() {
        return stage;
    }

    public String getMethod() {
        return method;
    }

    public String getRinger() {
        return ringer;
    }

    public QuarterSearchOptions ringer(String ringer) {
        this.ringer = ringer;
        return this;
    }

    public QuarterSearchOptions stage(String stage) {
        this.stage = stage;
        return this;
    }

    public QuarterSearchOptions method(String method) {
        this.method = method;
        return this;
    }

    public QuarterSearchOptions date(String date)
        throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        this.date = sdf.parse(date);
        return this;
    }
}

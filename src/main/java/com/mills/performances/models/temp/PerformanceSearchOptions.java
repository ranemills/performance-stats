package com.mills.performances.models.temp;

import com.mills.performances.enums.PerformanceProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ryan on 25/06/16.
 */
public class PerformanceSearchOptions {

    private String stage;
    private String method;
    private String ringer;
    private Date date;
    private Integer year;

    public PerformanceSearchOptions() {
    }

    public static PerformanceSearchOptions searchOptions(Map<String, String> requestParams)
        throws ParseException
    {
        PerformanceSearchOptions searchOptions = new PerformanceSearchOptions().stage(requestParams.get("stage"))
                                                                               .method(requestParams.get("method"))
                                                                               .ringer(requestParams.get("ringer"));

        if (requestParams.get("date") != null) {
            searchOptions.date(requestParams.get("date"));
        }
        if (requestParams.get("year") != null) {
            searchOptions.year(Integer.valueOf(requestParams.get("year")));
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

    public Integer getYear() {
        return year;
    }

    public PerformanceSearchOptions ringer(String ringer) {
        this.ringer = ringer;
        return this;
    }

    public PerformanceSearchOptions stage(String stage) {
        this.stage = stage;
        return this;
    }

    public PerformanceSearchOptions method(String method) {
        this.method = method;
        return this;
    }

    public PerformanceSearchOptions date(String date)
        throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        this.date = sdf.parse(date);
        return this;
    }

    public PerformanceSearchOptions year(Integer year)
    {
        this.year = year;
        return this;
    }

    public Map<PerformanceProperty, Object> toPerformanceProperties() {
        Map<PerformanceProperty, Object> properties = new HashMap<>();
        if(getMethod() != null) {
            properties.put(PerformanceProperty.METHOD, getMethod());
        }
        if(getStage() != null) {
            properties.put(PerformanceProperty.STAGE, getStage());
        }
        return properties;
    }

    public static PerformanceSearchOptions fromPerformanceProperties(Map<PerformanceProperty, Object> properties)
    {
        PerformanceSearchOptions searchOptions = new PerformanceSearchOptions();
        if(properties.containsKey(PerformanceProperty.STAGE)) {
            searchOptions.stage((String) properties.get(PerformanceProperty.STAGE));
        }
        if(properties.containsKey(PerformanceProperty.METHOD)) {
            searchOptions.method((String) properties.get(PerformanceProperty.METHOD));
        }
        if(properties.containsKey(PerformanceProperty.RINGER)) {
            searchOptions.ringer((String) properties.get(PerformanceProperty.RINGER));
        }
        return searchOptions;
    }
}

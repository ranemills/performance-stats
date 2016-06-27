package com.mills.performances.services;

import com.mills.performances.models.Performance;
import com.mills.performances.models.temp.PerformanceSearchOptions;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ryan on 27/06/16.
 */
public interface AlgoliaService {

    void addPerformances(List<Performance> performances);

    JSONObject getPerformances(PerformanceSearchOptions searchOptions);

}

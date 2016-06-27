package com.mills.performances.services.impl;

import com.algolia.search.saas.APIClient;
import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.mills.performances.models.Performance;
import com.mills.performances.models.temp.PerformanceSearchOptions;
import com.mills.performances.services.AlgoliaService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by ryan on 27/06/16.
 */
@Service
public class AlgoliaServiceImpl implements AlgoliaService {

    private static APIClient client = new APIClient("OB0ZGL0NJP", "7a8aed61da7a3d5e2f9ac19f4fb734ca");

    private static JSONArray fromPerformances(List<Performance> performances) {
        JSONArray array = new JSONArray();
        for (Performance performance : performances) {
            array.put(fromPerformance(performance));
        }
        return array;

    }

    private static JSONObject fromPerformance(Performance performance) {
        JSONObject object = new JSONObject();
        object.put("method_name", performance.getMethod());
        object.put("stage", performance.getStage());
        object.put("changes", performance.getChanges());
        object.put("location", performance.getLocation());
        object.put("date", performance.getDate());
        return object;
    }

    private static Query queryFromSearchOptions(PerformanceSearchOptions searchOptions) {
        Query query = new Query();
        if (searchOptions.getDate() != null) {
            query.setFacetFilters("date:" + searchOptions.getDate());
        }
        if (searchOptions.getStage() != null) {
            query.setFacetFilters("stage:" + searchOptions.getStage());
        }
        if (searchOptions.getMethod() != null) {
            query.setFacetFilters("method:" + searchOptions.getMethod());
        }
        System.out.println("===query===");
        System.out.println(query.getFacetFilters());
        return query;
    }

    @Override
    public void addPerformances(List<Performance> performances) {
        Index index = client.initIndex("performances");
        try {
            index.addObjects(fromPerformances(performances));
        } catch (AlgoliaException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public JSONObject getPerformances(PerformanceSearchOptions searchOptions) {
        Index index = client.initIndex("performances");
        try {
            JSONObject res = index.search(queryFromSearchOptions(searchOptions).setFacets(Collections.singletonList
                                                                                                          ("*")));
            System.out.println(res);
            return res;
        } catch (AlgoliaException e) {
            return new JSONObject();
        }
    }
}

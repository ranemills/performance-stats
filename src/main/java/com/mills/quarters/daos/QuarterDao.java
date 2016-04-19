package com.mills.quarters.daos;

import com.mills.quarters.models.temp.TempCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by ryan on 12/04/16.
 */
@Service
public class QuarterDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public Map<String, Integer> findMethodCounts(SearchOptions searchOptions) {
        return propertyCount("method", searchOptions);
    }

    public Map<String, Integer> findStageCounts(SearchOptions searchOptions) {
        return propertyCount("stage", searchOptions);
    }

    public Map<String, Integer> findRingerCounts(SearchOptions searchOptions) {
        Aggregation agg = newAggregation(
            match(criteriaFromSearchOptions(searchOptions)),
            unwind("$ringers"),
            project().and("$ringers.name").as("ringer"),
            group("ringer").count().as("count"),
            project("count").and("property").previousOperation(),
            sort(DESC, "count")
        );
        AggregationResults<TempCount> results = mongoTemplate.aggregate(agg, "quarter", TempCount.class);
        Map<String, Integer> outputMap = new HashMap<>();
        for (TempCount tempCount : results.getMappedResults()) {
            outputMap.put(tempCount.getProperty(), tempCount.getCount());
        }
        return outputMap;
    }

    private Map<String, Integer> propertyCount(String property, SearchOptions searchOptions) {
        Aggregation agg = newAggregation(
            match(criteriaFromSearchOptions(searchOptions)),
            project(property),
            group(property).count().as("count"),
            project("count").and("property").previousOperation(),
            sort(DESC, "count")
        );
        AggregationResults<TempCount> results = mongoTemplate.aggregate(agg, "quarter", TempCount.class);
        Map<String, Integer> outputMap = new HashMap<>();
        for (TempCount countObj : results.getMappedResults()) {
            outputMap.put(countObj.getProperty(), countObj.getCount());
        }
        return outputMap;
    }

    private Criteria criteriaFromSearchOptions(SearchOptions searchOptions) {
        Criteria criteria = new Criteria();
        if (searchOptions.getStage() != null) {
            criteria.and("stage").is(searchOptions.getStage());
        }
        return criteria;
    }

    public static class SearchOptions {

        private String stage;

        private SearchOptions() {
        }

        public static SearchOptions searchOptions(Map<String, String> requestParams) {
            SearchOptions options = new SearchOptions();
            options.stage = requestParams.get("stage");
            return options;
        }

        String getStage() {
            return stage;
        }

        public SearchOptions stage(String stage) {
            this.stage = stage;
            return this;
        }

    }
}

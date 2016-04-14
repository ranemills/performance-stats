package com.mills.quarters.daos;

import com.mills.quarters.models.temp.MethodCount;
import com.mills.quarters.models.temp.RingerCount;
import com.mills.quarters.models.temp.StageCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by ryan on 12/04/16.
 */
@Service
public class QuarterDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<MethodCount> findMethodCounts(SearchOptions searchOptions) {
        return propertyCount("method", MethodCount.class, searchOptions);
    }

    public List<StageCount> findStageCounts(SearchOptions searchOptions) {
        return propertyCount("stage", StageCount.class, searchOptions);
    }

    public List<RingerCount> findRingerCounts(SearchOptions searchOptions) {
        Aggregation agg = newAggregation(
            match(criteriaFromSearchOptions(searchOptions)),
            unwind("$ringers"),
            project().and("$ringers.name").as("ringer"),
            group("ringer").count().as("count"),
            project("count").and("ringer").previousOperation(),
            sort(DESC, "count")
        );
        AggregationResults<RingerCount> results = mongoTemplate.aggregate(agg, "quarter", RingerCount.class);
        return results.getMappedResults();
    }

    private <T> List<T> propertyCount(String property, Class c, SearchOptions searchOptions) {
        Aggregation agg = newAggregation(
            match(criteriaFromSearchOptions(searchOptions)),
            project(property),
            group(property).count().as("count"),
            project("count").and(property).previousOperation(),
            sort(DESC, "count")
        );
        AggregationResults<T> results = mongoTemplate.aggregate(agg, "quarter", c);
        return results.getMappedResults();
    }

    private Criteria criteriaFromSearchOptions(SearchOptions searchOptions) {
        Criteria criteria = new Criteria();
        if(searchOptions.getStage() != null) {
            criteria.and("stage").is(searchOptions.getStage());
        }
        return criteria;
    }

    public static class SearchOptions {

        private String stage;

        private SearchOptions() {
        }

        public static SearchOptions searchOptions(Map<String,String> requestParams) {
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

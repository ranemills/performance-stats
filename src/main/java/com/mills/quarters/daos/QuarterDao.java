package com.mills.quarters.daos;

import com.mills.quarters.models.Quarter;
import com.mills.quarters.models.temp.TempCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import static com.mills.quarters.daos.AbstractDao.customerCriteria;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

/**
 * Created by ryan on 12/04/16.
 */
@Service
public class QuarterDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Quarter> findQuarters(SearchOptions searchOptions) {
        return mongoTemplate.find(new Query().addCriteria(criteriaFromSearchOptions(searchOptions)), Quarter.class);
    }

    public List<TempCount> findMethodCounts(SearchOptions searchOptions) {
        return propertyCount("method", searchOptions);
    }

    public List<TempCount> findStageCounts(SearchOptions searchOptions) {
        return propertyCount("stage", searchOptions);
    }

    public List<TempCount> findRingerCounts(SearchOptions searchOptions) {
        Aggregation agg = newAggregation(
            match(criteriaFromSearchOptions(searchOptions)),
            unwind("$ringers"),
            project().and("$ringers.name").as("ringer"),
            group("ringer").count().as("count"),
            project("count").and("property").previousOperation(),
            sort(DESC, "count")
        );
        AggregationResults<TempCount> results = mongoTemplate.aggregate(agg, "quarter", TempCount.class);
        return results.getMappedResults();
    }

    private List<TempCount> propertyCount(String property, SearchOptions searchOptions) {
        Aggregation agg = newAggregation(
            match(criteriaFromSearchOptions(searchOptions)),
            project(property),
            group(property).count().as("count"),
            project("count").and("property").previousOperation(),
            sort(DESC, "count")
        );
        AggregationResults<TempCount> results = mongoTemplate.aggregate(agg, "quarter", TempCount.class);
        return results.getMappedResults();
    }

    private Criteria criteriaFromSearchOptions(SearchOptions searchOptions) {
        Criteria criteria = customerCriteria();
        try {
            if (searchOptions.getStage() != null) {
                criteria.and("stage").is(URLDecoder.decode(searchOptions.getStage(), "UTF-8"));
            }
            if (searchOptions.getMethod() != null) {
                criteria.and("method").is(URLDecoder.decode(searchOptions.getMethod(), "UTF-8"));
            }
            if (searchOptions.getRinger() != null) {
                criteria.and("ringers.name").is(URLDecoder.decode(searchOptions.getRinger(), "UTF-8"));
            }
        } catch (UnsupportedEncodingException ignored) {
        }

        return criteria;
    }

    public static class SearchOptions {

        private String stage;
        private String method;
        private String ringer;

        private SearchOptions() {
        }

        public static SearchOptions searchOptions(Map<String, String> requestParams) {
            return new SearchOptions().stage(requestParams.get("stage"))
                                      .method(requestParams.get("method"))
                                      .ringer(requestParams.get("ringer"));
        }

        String getStage() {
            return stage;
        }

        public String getMethod() {
            return method;
        }

        public String getRinger() {
            return ringer;
        }

        public SearchOptions ringer(String ringer) {
            this.ringer = ringer;
            return this;
        }

        public SearchOptions stage(String stage) {
            this.stage = stage;
            return this;
        }

        public SearchOptions method(String method) {
            this.method = method;
            return this;
        }
    }
}

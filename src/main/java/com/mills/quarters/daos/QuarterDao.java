package com.mills.quarters.daos;

import com.mills.quarters.models.temp.MethodCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by ryan on 12/04/16.
 */
@Service
public class QuarterDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<MethodCount> findMethodCounts() {
        Aggregation agg = newAggregation(
            project("method"),
            group("method").count().as("count"),
            project("count").and("method").previousOperation(),
            sort(DESC, "count")
        );
        AggregationResults<MethodCount> results = mongoTemplate.aggregate(agg, "quarter", MethodCount.class);
        return results.getMappedResults();
    }
}

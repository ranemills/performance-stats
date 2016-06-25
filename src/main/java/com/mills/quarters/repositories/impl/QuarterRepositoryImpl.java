package com.mills.quarters.repositories.impl;

import com.mills.quarters.models.Quarter;
import com.mills.quarters.models.temp.DateTempCount;
import com.mills.quarters.models.temp.QuarterSearchOptions;
import com.mills.quarters.models.temp.StringTempCount;
import com.mills.quarters.models.temp.TempCount;
import com.mills.quarters.repositories.QuarterCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static com.mills.quarters.utils.CustomerUtils.customerCriteria;
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
@Repository
public class QuarterRepositoryImpl implements QuarterCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    private static Criteria criteriaFromSearchOptions(QuarterSearchOptions searchOptions) {
        Criteria criteria = customerCriteria();
        try {
            if (searchOptions.getStage() != null) {
                criteria.and("stage").is(URLDecoder.decode(searchOptions.getStage(), "UTF-8"));
            }
            if (searchOptions.getMethod() != null) {
                criteria.and("method").is(URLDecoder.decode(searchOptions.getMethod(), "UTF-8"));
            }
            if (searchOptions.getDate() != null) {
                criteria.and("date").is(searchOptions.getDate());
            }
            if (searchOptions.getRinger() != null) {
                criteria.and("ringers.name").is(URLDecoder.decode(searchOptions.getRinger(), "UTF-8"));
            }
        } catch (UnsupportedEncodingException ignored) {
        }

        return criteria;
    }

    @Override
    public List<Quarter> findQuarters(QuarterSearchOptions searchOptions) {
        return mongoTemplate.find(new Query().addCriteria(criteriaFromSearchOptions(searchOptions)), Quarter.class);
    }

    @Override
    public List<StringTempCount> findMethodCounts(QuarterSearchOptions searchOptions) {
        return propertyCount("method", searchOptions);
    }

    @Override
    public List<StringTempCount> findStageCounts(QuarterSearchOptions searchOptions) {
        return propertyCount("stage", searchOptions);
    }

    @Override
    public List<DateTempCount> findDateCounts(QuarterSearchOptions searchOptions) {
        return propertyCount("date", searchOptions, DateTempCount.class);
    }

    @Override
    public List<StringTempCount> findRingerCounts(QuarterSearchOptions searchOptions) {
        Aggregation agg = newAggregation(
            match(criteriaFromSearchOptions(searchOptions)),
            unwind("$ringers"),
            project().and("$ringers.name").as("ringer"),
            group("ringer").count().as("count"),
            project("count").and("property").previousOperation(),
            sort(DESC, "count")
        );
        AggregationResults<StringTempCount> results = mongoTemplate.aggregate(agg, "quarter", StringTempCount.class);
        return results.getMappedResults();
    }

    private List<StringTempCount> propertyCount(String property, QuarterSearchOptions searchOptions) {
        return propertyCount(property, searchOptions, StringTempCount.class);
    }

    private <T extends TempCount> List<T> propertyCount(String property, QuarterSearchOptions searchOptions, Class
                                                                                                                 tempCount)
    {
        Aggregation agg = newAggregation(
            match(criteriaFromSearchOptions(searchOptions)),
            project(property),
            group(property).count().as("count"),
            project("count").and("property").previousOperation(),
            sort(DESC, "count")
        );
        AggregationResults<T> results = mongoTemplate.aggregate(agg, "quarter", tempCount);
        return results.getMappedResults();
    }

}

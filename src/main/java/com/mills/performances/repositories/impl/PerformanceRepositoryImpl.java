package com.mills.performances.repositories.impl;

import com.mills.performances.models.Performance;
import com.mills.performances.models.temp.DateTempCount;
import com.mills.performances.models.temp.IntegerTempCount;
import com.mills.performances.models.temp.PerformanceSearchOptions;
import com.mills.performances.models.temp.StringTempCount;
import com.mills.performances.models.temp.TempCount;
import com.mills.performances.repositories.PerformanceCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static com.mills.performances.MongoConfiguration.DOCUMENT_PERFORMANCE;
import static com.mills.performances.utils.CustomerUtils.customerCriteria;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.bind;
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
public class PerformanceRepositoryImpl implements PerformanceCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    private static Criteria criteriaFromSearchOptions(PerformanceSearchOptions searchOptions) {
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
            if (searchOptions.getYear() != null) {
                criteria.and("year").is(searchOptions.getYear());
            }
        } catch (UnsupportedEncodingException ignored) {
        }

        return criteria;
    }

    @Override
    public List<Performance> findPerformances(PerformanceSearchOptions searchOptions) {
        return mongoTemplate.find(new Query().addCriteria(criteriaFromSearchOptions(searchOptions)), Performance.class);
    }

    @Override
    public List<Performance> findPerformances(PerformanceSearchOptions searchOptions, Sort sort) {
        return mongoTemplate.find(new Query().addCriteria(criteriaFromSearchOptions(searchOptions)).with(sort), Performance.class);
    }

    @Override
    public List<StringTempCount> findMethodCounts(PerformanceSearchOptions searchOptions) {
        return propertyCount("method", searchOptions);
    }

    @Override
    public List<StringTempCount> findStageCounts(PerformanceSearchOptions searchOptions) {
        return propertyCount("stage", searchOptions);
    }

    @Override
    public List<DateTempCount> findDateCounts(PerformanceSearchOptions searchOptions) {
        return propertyCount("date", searchOptions, DateTempCount.class);
    }

    @Override
    public List<IntegerTempCount> findYearCounts(PerformanceSearchOptions searchOptions) {
        Aggregation agg = newAggregation(
            match(criteriaFromSearchOptions(searchOptions)),
            project().andExpression("year($date)").as("year"),
            group("year").count().as("count"),
            project("count").and("property").previousOperation(),
            sort(DESC, "property")
        );
        AggregationResults<IntegerTempCount> results = mongoTemplate.aggregate(agg, DOCUMENT_PERFORMANCE, IntegerTempCount.class);
        return results.getMappedResults();
    }

    @Override
    public List<StringTempCount> findRingerCounts(PerformanceSearchOptions searchOptions) {
        Aggregation agg = newAggregation(
            match(criteriaFromSearchOptions(searchOptions)),
            unwind("$ringers"),
            project().and("$ringers.name").as("ringer"),
            group("ringer").count().as("count"),
            project("count").and("property").previousOperation(),
            sort(DESC, "count")
        );
        AggregationResults<StringTempCount> results = mongoTemplate.aggregate(agg, DOCUMENT_PERFORMANCE,
                                                                              StringTempCount.class);
        return results.getMappedResults();
    }

    private List<StringTempCount> propertyCount(String property, PerformanceSearchOptions searchOptions) {
        return propertyCount(property, searchOptions, StringTempCount.class);
    }

    private <T extends TempCount> List<T> propertyCount(String property, PerformanceSearchOptions searchOptions,
                                                        Class tempCount)
    {
        Aggregation agg = newAggregation(
            match(criteriaFromSearchOptions(searchOptions)),
            project(property),
            group(property).count().as("count"),
            project("count").and("property").previousOperation(),
            sort(DESC, "count")
        );
        AggregationResults<T> results = mongoTemplate.aggregate(agg, DOCUMENT_PERFORMANCE, tempCount);
        return results.getMappedResults();
    }

}

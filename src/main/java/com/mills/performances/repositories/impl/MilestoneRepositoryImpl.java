package com.mills.performances.repositories.impl;

import com.mills.performances.models.Milestone;
import com.mills.performances.models.Performance;
import com.mills.performances.repositories.MilestoneCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mills.performances.utils.CustomerUtils.customerCriteria;

/**
 * Created by ryan on 09/10/16.
 */
@Repository
public class MilestoneRepositoryImpl implements MilestoneCustomRepository {

    @Autowired
    private MongoTemplate _mongoTemplate;

    @Override
    public List<Milestone> findAll(Sort sort) {
        return _mongoTemplate.find(new Query().addCriteria(customerCriteria()).with(sort), Milestone.class);
    }
}

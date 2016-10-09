package com.mills.performances.repositories.impl;

import com.mills.performances.repositories.MongoModelRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.util.List;

import static com.mills.performances.utils.CustomerUtils.customerCriteria;

/**
 * Created by ryan on 09/10/16.
 */
public class MongoModelRepositoryImpl<T> extends SimpleMongoRepository<T, ObjectId> implements MongoModelRepository<T> {

    private MongoOperations _mongoOperations;
    private MongoEntityInformation<T, ObjectId> _entityInformation;

    /**
     * Creates a new {@link SimpleMongoRepository} for the given {@link MongoEntityInformation} and
     * {@link MongoTemplate}.
     *
     * @param metadata        must not be {@literal null}.
     * @param mongoOperations must not be {@literal null}.
     */
    public MongoModelRepositoryImpl(MongoEntityInformation<T, ObjectId> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);

        _mongoOperations = mongoOperations;
        _entityInformation = metadata;
    }

    public List<T> findAll() {
        return _mongoOperations.find(new Query().addCriteria(customerCriteria()), _entityInformation.getJavaType());
    }

    public List<T> findAll(Sort sort) {
        return _mongoOperations.find(new Query().addCriteria(customerCriteria()).with(sort), _entityInformation.getJavaType());
    }
}

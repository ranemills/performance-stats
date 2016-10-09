package com.mills.performances.repositories;

import org.bson.types.ObjectId;
import org.omg.CORBA.Object;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by ryan on 09/10/16.
 */
@NoRepositoryBean
public interface MongoModelRepository<T>
    extends MongoRepository<T, ObjectId> {

}

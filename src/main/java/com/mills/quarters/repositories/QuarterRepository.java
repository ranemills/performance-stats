package com.mills.quarters.repositories;

import com.mills.quarters.models.Quarter;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ryan on 12/04/16.
 */
@org.springframework.stereotype.Repository
public interface QuarterRepository extends MongoRepository<Quarter, Long> {

}

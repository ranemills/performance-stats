package com.mills.performances.repositories;

import com.mills.performances.models.Performance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface QuarterRepository extends MongoRepository<Performance, Long>, QuarterCustomRepository {

}

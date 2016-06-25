package com.mills.quarters.repositories;

import com.mills.quarters.models.Quarter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface QuarterRepository extends MongoRepository<Quarter, Long>, QuarterCustomRepository {

}

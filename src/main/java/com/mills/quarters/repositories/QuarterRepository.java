package com.mills.quarters.repositories;

import com.mills.quarters.models.Quarter;
import com.mills.quarters.models.temp.MethodCount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Created by ryan on 12/04/16.
 */
@org.springframework.stereotype.Repository
public interface QuarterRepository extends MongoRepository<Quarter, Long> {

}

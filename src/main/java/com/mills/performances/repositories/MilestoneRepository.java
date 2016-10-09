package com.mills.performances.repositories;

import com.mills.performances.models.Milestone;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface MilestoneRepository extends MongoModelRepository<Milestone> {

}

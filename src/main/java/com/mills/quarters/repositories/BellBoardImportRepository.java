package com.mills.quarters.repositories;

import com.mills.quarters.models.BellBoardImport;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ryan on 12/04/16.
 */
@org.springframework.stereotype.Repository
public interface BellBoardImportRepository extends MongoRepository<BellBoardImport, Long> {

}

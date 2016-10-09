package com.mills.performances.repositories;

import com.mills.performances.models.BellBoardImport;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ryan on 12/04/16.
 */
@org.springframework.stereotype.Repository
public interface BellBoardImportRepository extends MongoRepository<BellBoardImport, Long> {

    BellBoardImport findByName(String name);
}

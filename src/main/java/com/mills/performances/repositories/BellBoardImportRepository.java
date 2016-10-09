package com.mills.performances.repositories;

import com.mills.performances.models.BellBoardImport;
import org.springframework.stereotype.Repository;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface BellBoardImportRepository extends MongoModelRepository<BellBoardImport> {

    BellBoardImport findByName(String name);
}

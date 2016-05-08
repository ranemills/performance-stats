package com.mills.quarters.services;

import com.mills.quarters.models.BellBoardImport;
import com.mills.quarters.repositories.BellBoardImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ryan on 08/05/16.
 */
@Service
public class BellBoardImportService {

    @Autowired
    private BellBoardImportRepository bellBoardImportRepository;

    public BellBoardImport addImport(String inUrl) {

        String outUrl = inUrl.replace("search.php", "export.php");

        BellBoardImport bbImport = new BellBoardImport();
        bbImport = MongoService.setCustomer(bbImport);
        bbImport.setUrl(outUrl);
        bbImport.setName("bellboard");
        return bellBoardImportRepository.save(bbImport);
    }

}

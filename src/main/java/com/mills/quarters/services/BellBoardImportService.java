package com.mills.quarters.services;

import com.mills.quarters.models.BellBoardImport;
import com.mills.quarters.models.Quarter;
import com.mills.quarters.repositories.BellBoardImportRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by ryan on 08/05/16.
 */
@Service
public class BellBoardImportService {

    @Autowired
    private BellBoardImportRepository bellBoardImportRepository;

    @Autowired
    private BellBoardService bellBoardService;

    @Autowired
    AuthUserService authUserService;

    public BellBoardImport addImport(String inUrl)
    {
        return addImport("bellboard", inUrl);
    }

    public BellBoardImport addImport(String name, String inUrl)
    {
        String outUrl = inUrl.replace("search.php", "export.php");

        BellBoardImport bbImport = new BellBoardImport(outUrl);
        bbImport = MongoService.setCustomer(bbImport);
        bbImport.setName(name);
        return bellBoardImportRepository.save(bbImport);
    }

    public List<Quarter> runImport(BellBoardImport bbImport)
        throws URISyntaxException
    {
        List<Quarter> res = bellBoardService.addPerformances(bbImport);
        bbImport.setLastImport(DateTime.now().toDate());
        bellBoardImportRepository.save(bbImport);
        authUserService.setCurrentUserAsImported();
        return res;
    }
}

package com.mills.quarters.services.impl;

import com.mills.quarters.models.BellBoardImport;
import com.mills.quarters.models.Quarter;
import com.mills.quarters.repositories.BellBoardImportRepository;
import com.mills.quarters.services.AuthUserService;
import com.mills.quarters.services.BellBoardImportService;
import com.mills.quarters.services.BellBoardService;
import com.mills.quarters.utils.CustomerUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by ryan on 08/05/16.
 */
@Service
public class BellBoardImportServiceImpl implements BellBoardImportService {

    @Autowired
    AuthUserService authUserService;
    @Autowired
    private BellBoardImportRepository bellBoardImportRepository;
    @Autowired
    private BellBoardService bellBoardService;

    @Override
    public BellBoardImport addImport(String inUrl)
    {
        return addImport("bellboard", inUrl);
    }

    @Override
    public BellBoardImport addImport(String name, String inUrl)
    {
        String outUrl = inUrl.replace("search.php", "export.php");

        BellBoardImport bbImport = new BellBoardImport(outUrl);
        bbImport = CustomerUtils.setCustomer(bbImport);
        bbImport.setName(name);
        return bellBoardImportRepository.save(bbImport);
    }

    @Override
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

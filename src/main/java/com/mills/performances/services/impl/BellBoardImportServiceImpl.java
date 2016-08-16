package com.mills.performances.services.impl;

import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.Performance;
import com.mills.performances.repositories.BellBoardImportRepository;
import com.mills.performances.repositories.PerformanceRepository;
import com.mills.performances.services.AuthUserService;
import com.mills.performances.services.BellBoardImportService;
import com.mills.performances.services.BellBoardService;
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
    private AuthUserService _authUserService;
    @Autowired
    private BellBoardImportRepository _bellBoardImportRepository;
    @Autowired
    private BellBoardService _bellBoardService;
    @Autowired
    private PerformanceRepository _performanceRepository;

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
        bbImport.setName(name);
        return _bellBoardImportRepository.save(bbImport);
    }

    @Override
    public List<Performance> runImport(BellBoardImport bbImport)
        throws URISyntaxException
    {
        List<Performance> res = _bellBoardService.loadPerformances(bbImport);
        _performanceRepository.save(res);

        bbImport.setLastImport(DateTime.now().toDate());
        _bellBoardImportRepository.save(bbImport);

        _authUserService.setCurrentUserAsImported();

        return res;
    }
}

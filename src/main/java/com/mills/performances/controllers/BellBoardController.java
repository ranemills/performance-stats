package com.mills.performances.controllers;

import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.Performance;
import com.mills.performances.repositories.BellBoardImportRepository;
import com.mills.performances.services.BellBoardImportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by ryan on 23/04/16.
 */
@SuppressWarnings("unused")
@RequestMapping("/api/bellboard")
@RestController
public class BellBoardController {

    @Autowired
    private BellBoardImportService _bellBoardImportService;

    @Autowired
    private BellBoardImportRepository _bellBoardImportRepository;

    @RequestMapping("/import")
    public List<Performance> importPerformances(@RequestParam(required = false) String name,
                                                @RequestParam(required = true) String bbUrl)
    {
        try {
            BellBoardImport bbImport;
            if (!StringUtils.isNotEmpty(name)) {
                bbImport = _bellBoardImportService.addImport(name, bbUrl);
            } else {
                bbImport = _bellBoardImportService.addImport(bbUrl);
            }
            return _bellBoardImportService.runImport(bbImport);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL");
        }
    }

    @RequestMapping("/imports")
    public List<BellBoardImport> listImports()
    {
        return _bellBoardImportRepository.findAll();
    }
}

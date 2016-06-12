package com.mills.quarters.controllers;

import com.mills.quarters.models.BellBoardImport;
import com.mills.quarters.models.Quarter;
import com.mills.quarters.repositories.BellBoardImportRepository;
import com.mills.quarters.services.BellBoardImportService;
import com.mills.quarters.services.BellBoardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    private BellBoardService _bellBoardService;

    @Autowired
    private BellBoardImportService bellBoardImportService;

    @Autowired
    private BellBoardImportRepository bellBoardImportRepository;

    @RequestMapping("/import")
    public List<Quarter> importPerformances(@RequestParam(required = false) String name, @RequestParam(required = true) String bbUrl)
    {
        try {
            BellBoardImport bbImport;
            if(!StringUtils.isNotEmpty(name)) {
                bbImport = bellBoardImportService.addImport(name, bbUrl);
            } else {
                bbImport = bellBoardImportService.addImport(bbUrl);
            }
            return bellBoardImportService.runImport(bbImport);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL");
        }
    }

    @RequestMapping("/imports")
    public List<BellBoardImport> listImports()
    {
        return bellBoardImportRepository.findAll();
    }
}

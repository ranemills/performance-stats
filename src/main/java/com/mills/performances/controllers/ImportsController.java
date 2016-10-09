package com.mills.performances.controllers;

import com.mills.performances.models.BellBoardImport;
import com.mills.performances.repositories.BellBoardImportRepository;
import com.mills.performances.services.BellBoardImportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by ryan on 23/04/16.
 */
@SuppressWarnings("unused")
@RequestMapping("/api")
@RestController
public class ImportsController {

    @Autowired
    private BellBoardImportService _bellBoardImportService;

    @Autowired
    private BellBoardImportRepository _bellBoardImportRepository;

    @RequestMapping(value = "/imports", method = RequestMethod.POST)
    public BellBoardImport importPerformances(@RequestBody Map<String,String> requestBody)
    {
        String name = requestBody.get("name");
        String bbUrl = requestBody.get("bbUrl");

        BellBoardImport bbImport;
        if (StringUtils.isNotEmpty(name)) {
            bbImport = _bellBoardImportService.addImport(name, bbUrl);
        } else {
            bbImport = _bellBoardImportService.addImport(bbUrl);
        }
        _bellBoardImportService.runImport(bbImport);

        return bbImport;
    }

    @RequestMapping("/imports")
    public List<BellBoardImport> listImports()
    {
        return _bellBoardImportRepository.findAll();
    }

    @RequestMapping("/imports/{importName}/update")
    public void refreshImport(@PathVariable String importName)
    {
        BellBoardImport bellBoardImport = _bellBoardImportRepository.findByName(importName);
        _bellBoardImportService.runImport(bellBoardImport);
    }
}

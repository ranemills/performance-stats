package com.mills.performances.services;

import com.mills.performances.AbstractTest;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.Performance;
import com.mills.performances.repositories.BellBoardImportRepository;
import com.mills.performances.repositories.PerformanceRepository;
import com.mills.performances.services.impl.BellBoardImportServiceImpl;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.mills.performances.builders.PerformanceBuilder.tritonDelightPerformance;
import static com.mills.performances.builders.PerformanceBuilder.yorkshireMajorPerformance;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by ryan on 16/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BellBoardImportServiceImplTest
    extends AbstractTest {

    @Mock
    private AuthUserService _authUserService;
    @Mock
    private BellBoardImportRepository _bellBoardImportRepository;
    @Mock
    private BellBoardService _bellBoardService;
    @Mock
    private PerformanceRepository _performanceRepository;
    @Mock
    private AlgoliaService _algoliaServicce;
    @InjectMocks
    private BellBoardImportServiceImpl _bellBoardImportService;

    private static final String searchUrl = "http://bb.ringingworld.co.uk/search.php?ringer=ryan+mills&length=q-or-p&bells_type=tower";
    private static final String exportUrl = "http://bb.ringingworld.co.uk/export.php?ringer=ryan+mills&length=q-or-p&bells_type=tower";

    @After
    public void cleanup() {
        DateTimeUtils.setCurrentMillisSystem();
    }

    @Test
    public void addImportTransformsTheUrl()
    {
        String importName = "test";
        BellBoardImport expectedImport = new BellBoardImport(exportUrl);
        expectedImport.setName(importName);

        _bellBoardImportService.addImport(importName, searchUrl);

        verify(_bellBoardImportRepository).save(expectedImport);
    }

    @Test
    public void addImportUsesDefaultName()
    {
        BellBoardImport expectedImport = new BellBoardImport(exportUrl);
        expectedImport.setName("bellboard");

        _bellBoardImportService.addImport(searchUrl);

        verify(_bellBoardImportRepository).save(expectedImport);
    }

    @Test
    public void runImportSavesPerformances()
        throws Exception
    {
        DateTimeUtils.setCurrentMillisFixed(10L);

        Performance performance1 = yorkshireMajorPerformance().build();
        Performance performance2 = tritonDelightPerformance().build();

        BellBoardImport bbImport = new BellBoardImport(exportUrl);
        bbImport.setName("test");
        BellBoardImport expectedBbImport = new BellBoardImport(exportUrl);
        expectedBbImport.setName("test");
        expectedBbImport.setLastImport(DateTime.now().toDate());

        List<Performance> performanceList = Arrays.asList(performance1, performance2);

        given(_bellBoardService.loadPerformances(bbImport)).willReturn(performanceList);

        _bellBoardImportService.runImport(bbImport);

        verify(_performanceRepository).save(performanceList);
        verify(_algoliaServicce).addPerformances(performanceList);
        verify(_bellBoardImportRepository).save(expectedBbImport);
        verify(_authUserService).setCurrentUserAsImported();
    }
}

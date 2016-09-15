package com.mills.performances.services;

import com.mills.bellboard.models.xml.BBPerformance;
import com.mills.bellboard.services.BellBoardService;
import com.mills.performances.AbstractTest;
import com.mills.performances.builders.MilestoneFacetBuilder;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.MilestoneFacet;
import com.mills.performances.models.Performance;
import com.mills.performances.repositories.MilestoneFacetRepository;
import com.mills.performances.services.impl.BellBoardServiceImpl;
import com.mills.performances.services.impl.MilestoneServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.mills.bellboard.models.BBPerformanceBuilder.tritonDelightBbPerformance;
import static com.mills.bellboard.models.BBPerformanceBuilder.yorkshireMajorBbPerformance;
import static com.mills.performances.builders.MilestoneFacetBuilder.milestoneFacetBuilder;
import static com.mills.performances.builders.PerformanceBuilder.tritonDelightPerformance;
import static com.mills.performances.builders.PerformanceBuilder.yorkshireMajorPerformance;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by ryan on 23/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MilestoneServiceImplTest extends AbstractTest {

    @Mock
    private MilestoneFacetRepository _milestoneFacetRepository;
    @InjectMocks
    private MilestoneServiceImpl _milestoneService;

    Performance performance1;
    Performance performance2;

    @Before
    public void setup()
    {
        performance1 = yorkshireMajorPerformance().build();
        performance2 = tritonDelightPerformance().build();
    }

    @Test
    public void updatesCountForNullMilestoneFacet()
    {
        MilestoneFacet facet = milestoneFacetBuilder().build();
        given(_milestoneFacetRepository.findAll()).willReturn(Collections.singletonList(facet));

        _milestoneService.updateMilestones(Collections.singletonList(performance1));

        MilestoneFacet expectedFacet = milestoneFacetBuilder().build();
        expectedFacet.incrementCount();

        verify(_milestoneFacetRepository).save(expectedFacet);
    }

}
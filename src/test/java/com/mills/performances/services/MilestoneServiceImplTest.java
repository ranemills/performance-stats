package com.mills.performances.services;

import com.mills.performances.AbstractTest;
import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.MilestoneFacet;
import com.mills.performances.models.Performance;
import com.mills.performances.repositories.MilestoneFacetRepository;
import com.mills.performances.services.impl.MilestoneServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static com.mills.performances.builders.MilestoneFacetBuilder.milestoneFacetBuilder;
import static com.mills.performances.builders.PerformanceBuilder.tritonDelightPerformance;
import static com.mills.performances.builders.PerformanceBuilder.yorkshireMajorPerformance;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by ryan on 23/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MilestoneServiceImplTest extends AbstractTest {

    @Mock
    private MilestoneFacetRepository _milestoneFacetRepository;
    @Mock
    private PerformanceService _performanceService;
    @InjectMocks
    private MilestoneServiceImpl _milestoneService;

    private Performance _performance1;
    private Performance _performance2;

    @Before
    public void setup() {
        _performance1 = yorkshireMajorPerformance().build();
        _performance2 = tritonDelightPerformance().build();
    }

    @Test
    public void updatesCountForNullMilestoneFacet() {
        MilestoneFacet facet = milestoneFacetBuilder().build();
        given(_milestoneFacetRepository.findAll()).willReturn(Collections.singletonList(facet));

        _milestoneService.updateMilestones(Collections.singletonList(_performance1));

        MilestoneFacet expectedFacet = milestoneFacetBuilder().build();
        expectedFacet.incrementCount();

        verify(_milestoneFacetRepository).save(Collections.singletonList(expectedFacet));
    }

    @Test
    public void updatesCountForMethodMilestoneFacet() {
        MilestoneFacet facet = milestoneFacetBuilder().addPropertyValue(PerformanceProperty.METHOD, "Triton Delight").build();
        given(_milestoneFacetRepository.findAll()).willReturn(Collections.singletonList(facet));
        given(_performanceService.propertiesMatch(_performance1, PerformanceProperty.METHOD, "Triton Delight")).willReturn(false);
        given(_performanceService.propertiesMatch(_performance2, PerformanceProperty.METHOD, "Triton Delight")).willReturn(true);

        _milestoneService.updateMilestones(Arrays.asList(_performance1, _performance2));

        MilestoneFacet expectedFacet = milestoneFacetBuilder().addPropertyValue(PerformanceProperty.METHOD, "Triton Delight").build();
        expectedFacet.incrementCount();

        verify(_milestoneFacetRepository).save(Collections.singletonList(expectedFacet));
    }

    @Test
    public void updatesCountForCombinedMilestoneFacet() {
        MilestoneFacet facet = milestoneFacetBuilder().addPropertyValue(PerformanceProperty.METHOD, "Triton Delight")
                .addPropertyValue(PerformanceProperty.STAGE, "Royal")
                .build();
        given(_milestoneFacetRepository.findAll()).willReturn(Collections.singletonList(facet));
        given(_performanceService.propertiesMatch(_performance1, PerformanceProperty.METHOD, "Triton Delight")).willReturn(false);
        given(_performanceService.propertiesMatch(_performance1, PerformanceProperty.STAGE, "Royal")).willReturn(false);
        given(_performanceService.propertiesMatch(_performance2, PerformanceProperty.METHOD, "Triton Delight")).willReturn(true);
        given(_performanceService.propertiesMatch(_performance2, PerformanceProperty.STAGE, "Royal")).willReturn(true);

        _milestoneService.updateMilestones(Arrays.asList(_performance1, _performance2));

        MilestoneFacet expectedFacet = milestoneFacetBuilder().addPropertyValue(PerformanceProperty.METHOD, "Triton Delight")
                .addPropertyValue(PerformanceProperty.STAGE, "Royal")
                .build();
        expectedFacet.incrementCount();

        verify(_milestoneFacetRepository).save(Collections.singletonList(expectedFacet));
    }

    @Test
    public void doesNotUpdateCountForPartialMatchingCombinedMilestoneFacet() {
        MilestoneFacet facet = milestoneFacetBuilder().addPropertyValue(PerformanceProperty.METHOD, "Triton Delight")
                .addPropertyValue(PerformanceProperty.STAGE, "Royal")
                .build();
        given(_milestoneFacetRepository.findAll()).willReturn(Collections.singletonList(facet));
        given(_performanceService.propertiesMatch(_performance2, PerformanceProperty.METHOD, "Triton Delight")).willReturn(false);
        given(_performanceService.propertiesMatch(_performance2, PerformanceProperty.STAGE, "Royal")).willReturn(true);

        _milestoneService.updateMilestones(Collections.singletonList(_performance2));

        MilestoneFacet expectedFacet = milestoneFacetBuilder().addPropertyValue(PerformanceProperty.METHOD, "Triton Delight")
                .addPropertyValue(PerformanceProperty.STAGE, "Royal")
                .build();

        verify(_milestoneFacetRepository).save(Collections.singletonList(expectedFacet));
    }

    @Test
    public void canCreateInitialMilestoneFacet() {
        _milestoneService.createInitialMilestoneFacets();

        MilestoneFacet expectedFacet = milestoneFacetBuilder().build();
        verify(_milestoneFacetRepository).save(expectedFacet);
    }

}
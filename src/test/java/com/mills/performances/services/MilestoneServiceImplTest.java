package com.mills.performances.services;

import com.mills.performances.AbstractTest;
import com.mills.performances.enums.MilestoneFacetType;
import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.Milestone;
import com.mills.performances.models.MilestoneFacet;
import com.mills.performances.models.Performance;
import com.mills.performances.repositories.MilestoneFacetRepository;
import com.mills.performances.repositories.MilestoneRepository;
import com.mills.performances.repositories.PerformanceRepository;
import com.mills.performances.services.impl.MilestoneServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static com.mills.performances.builders.MilestoneFacetBuilder.milestoneFacetBuilder;
import static com.mills.performances.builders.PerformanceBuilder.tritonDelightPerformance;
import static com.mills.performances.builders.PerformanceBuilder.yorkshireMajorPerformance;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
    private MilestoneRepository _milestoneRepository;
    @Mock
    private PerformanceService _performanceService;
    @Mock
    private PerformanceRepository _performanceRepository;
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
        MilestoneFacet facet = milestoneFacetBuilder(null).build();
        given(_milestoneFacetRepository.findAll()).willReturn(Collections.singletonList(facet));

        _milestoneService.updateMilestones(Collections.singletonList(_performance1));

        MilestoneFacet expectedFacet = milestoneFacetBuilder(null).build();
        expectedFacet.addValue(1);
        expectedFacet.addMilestone(1, _performance1);

        verify(_milestoneFacetRepository).save(Collections.singletonList(expectedFacet));
        verify(_milestoneRepository).save(Collections.singletonList(new Milestone(1, _performance1, new HashMap<>())));
    }

    @Test
    public void updatesCountForMethodMilestoneFacet() {
        MilestoneFacet facet = milestoneFacetBuilder(null).addPropertyValue(PerformanceProperty.METHOD, "Triton " +
                                                                                                        "Delight")
                                                          .build();
        given(_milestoneFacetRepository.findAll()).willReturn(Collections.singletonList(facet));
        given(_performanceService.propertiesMatch(_performance1, PerformanceProperty.METHOD, "Triton Delight"))
            .willReturn(false);
        given(_performanceService.propertiesMatch(_performance2, PerformanceProperty.METHOD, "Triton Delight"))
            .willReturn(true);

        _milestoneService.updateMilestones(Arrays.asList(_performance1, _performance2));

        MilestoneFacet expectedFacet = milestoneFacetBuilder(null).addPropertyValue(PerformanceProperty.METHOD,
                                                                                    "Triton Delight").build();
        expectedFacet.addValue(1);
        expectedFacet.addMilestone(1, _performance2);

        verify(_milestoneFacetRepository).save(Collections.singletonList(expectedFacet));
    }

    @Test
    public void updatesDurationForMethodMilestoneFacet() {
        MilestoneFacet facet = milestoneFacetBuilder(null).addPropertyValue(PerformanceProperty.METHOD,
                                                                            "Triton Delight")
                                                          .setType(MilestoneFacetType.DURATION)
                                                          .build();

        given(_milestoneFacetRepository.findAll()).willReturn(Collections.singletonList(facet));
        given(_performanceService.propertiesMatch(_performance1, PerformanceProperty.METHOD, "Triton Delight"))
            .willReturn(false);
        given(_performanceService.propertiesMatch(_performance2, PerformanceProperty.METHOD, "Triton Delight"))
            .willReturn(true);

        _milestoneService.updateMilestones(Arrays.asList(_performance1, _performance2));

        MilestoneFacet expectedFacet = milestoneFacetBuilder(null).addPropertyValue(PerformanceProperty.METHOD,
                                                                                    "Triton Delight").build();
        expectedFacet.addValue(_performance2.getTime());

        verify(_milestoneFacetRepository).save(Collections.singletonList(expectedFacet));
    }

    @Test
    public void updatesCountForCombinedMilestoneFacet() {
        MilestoneFacet facet = milestoneFacetBuilder(null).addPropertyValue(PerformanceProperty.METHOD, "Triton " +
                                                                                                        "Delight")
                                                          .addPropertyValue(PerformanceProperty.STAGE, "Royal")
                                                          .build();
        given(_milestoneFacetRepository.findAll()).willReturn(Collections.singletonList(facet));
        given(_performanceService.propertiesMatch(_performance1, PerformanceProperty.METHOD, "Triton Delight"))
            .willReturn(false);
        given(_performanceService.propertiesMatch(_performance1, PerformanceProperty.STAGE, "Royal")).willReturn(false);
        given(_performanceService.propertiesMatch(_performance2, PerformanceProperty.METHOD, "Triton Delight"))
            .willReturn(true);
        given(_performanceService.propertiesMatch(_performance2, PerformanceProperty.STAGE, "Royal")).willReturn(true);

        _milestoneService.updateMilestones(Arrays.asList(_performance1, _performance2));

        MilestoneFacet expectedFacet = milestoneFacetBuilder(null).addPropertyValue(PerformanceProperty.METHOD,
                                                                                    "Triton " +
                                                                                    "Delight")
                                                                  .addPropertyValue(PerformanceProperty.STAGE, "Royal")
                                                                  .build();
        expectedFacet.addValue(1);
        expectedFacet.addMilestone(1, _performance2);

        verify(_milestoneFacetRepository).save(Collections.singletonList(expectedFacet));
    }

    @Test
    public void doesNotUpdateCountForPartialMatchingCombinedMilestoneFacet() {
        MilestoneFacet facet = milestoneFacetBuilder(null).addPropertyValue(PerformanceProperty.METHOD, "Triton " +
                                                                                                        "Delight")
                                                          .addPropertyValue(PerformanceProperty.STAGE, "Royal")
                                                          .build();
        given(_milestoneFacetRepository.findAll()).willReturn(Collections.singletonList(facet));
        given(_performanceService.propertiesMatch(_performance2, PerformanceProperty.METHOD, "Triton Delight"))
            .willReturn(false);
        given(_performanceService.propertiesMatch(_performance2, PerformanceProperty.STAGE, "Royal")).willReturn(true);

        _milestoneService.updateMilestones(Collections.singletonList(_performance2));

        MilestoneFacet expectedFacet = milestoneFacetBuilder(null).addPropertyValue(PerformanceProperty.METHOD,
                                                                                    "Triton " +
                                                                                    "Delight")
                                                                  .addPropertyValue(PerformanceProperty.STAGE, "Royal")
                                                                  .build();

        verify(_milestoneFacetRepository).save(Collections.singletonList(expectedFacet));
    }

    @Test
    public void canCreateInitialMilestoneFacet() {
        _milestoneService.createInitialMilestoneFacets(null);

        MilestoneFacet expectedFacet = milestoneFacetBuilder(null).build();
        verify(_milestoneFacetRepository).save(expectedFacet);
    }

    @Test
    public void canIncrementCountAndSave() {
        MilestoneFacet facet = milestoneFacetBuilder(null).addPropertyValue(PerformanceProperty.METHOD, "Triton " +
                                                                                                        "Delight")
                                                          .setInitialCount(1)
                                                          .build();

        _milestoneService.incrementCount(facet, _performance1, true);

        MilestoneFacet expectedFacet = milestoneFacetBuilder(null).addPropertyValue(PerformanceProperty.METHOD,
                                                                                    "Triton Delight")
                                                                  .setInitialCount(2)
                                                                  .build();

        verify(_milestoneFacetRepository).save(expectedFacet);
    }

    @Test
    public void addsMilestoneOnIncrementCount() {
        MilestoneFacet facet = milestoneFacetBuilder(null).addPropertyValue(PerformanceProperty.METHOD, "Triton " +
                                                                                                        "Delight")
                                                          .build();

        _milestoneService.incrementCount(facet, _performance1, true);

        MilestoneFacet expectedFacet = milestoneFacetBuilder(null).addPropertyValue(PerformanceProperty.METHOD,
                                                                                    "Triton Delight")
                                                                  .setInitialCount(1)
                                                                  .build();
        expectedFacet.addMilestone(1, _performance1);

        verify(_milestoneFacetRepository).save(expectedFacet);
    }

    @Test
    public void createsMilestonesInDateOrder() {
        MilestoneFacet facet = milestoneFacetBuilder(null).build();
        given(_milestoneFacetRepository.findAll()).willReturn(Collections.singletonList(facet));

        _milestoneService.updateMilestones(Arrays.asList(_performance1, _performance2));

        MilestoneFacet expectedFacet = milestoneFacetBuilder(null).setInitialCount(2)
                                                                  .build();
        expectedFacet.addMilestone(1, _performance2);
        verify(_milestoneFacetRepository).save(Collections.singletonList(expectedFacet));
    }

    @Test
    public void milestoneCounts() {
        assertThat(_milestoneService.isMilestoneCount(1), is(true));
        assertThat(_milestoneService.isMilestoneCount(2), is(false));
        assertThat(_milestoneService.isMilestoneCount(5), is(true));
        assertThat(_milestoneService.isMilestoneCount(10), is(true));
        assertThat(_milestoneService.isMilestoneCount(25), is(true));
        assertThat(_milestoneService.isMilestoneCount(50), is(true));
        assertThat(_milestoneService.isMilestoneCount(74), is(false));
        assertThat(_milestoneService.isMilestoneCount(100), is(true));
        assertThat(_milestoneService.isMilestoneCount(101), is(false));
        assertThat(_milestoneService.isMilestoneCount(150), is(true));
        assertThat(_milestoneService.isMilestoneCount(203), is(false));
    }

    @Test
    public void milestoneDurations() {
        assertThat(_milestoneService.isMilestoneDuration(5*60), is(true));
        assertThat(_milestoneService.isMilestoneDuration(10*60), is(true));
        assertThat(_milestoneService.isMilestoneDuration(25*60), is(true));
        assertThat(_milestoneService.isMilestoneDuration(26*60), is(false));
        assertThat(_milestoneService.isMilestoneDuration(50*60), is(true));
        assertThat(_milestoneService.isMilestoneDuration(51*60), is(false));
        assertThat(_milestoneService.isMilestoneDuration(71*60), is(false));
        assertThat(_milestoneService.isMilestoneDuration(100*60), is(true));
        assertThat(_milestoneService.isMilestoneDuration(250*60), is(true));
        assertThat(_milestoneService.isMilestoneDuration(85), is(false));
        assertThat(_milestoneService.isMilestoneDuration(141), is(false));
        assertThat(_milestoneService.isMilestoneDuration(1001), is(false));
    }

    @Test
    public void canInitialiseNewFacet() {
        given(_performanceService.findByProperties(new HashMap<>(), new Sort(Sort.Direction.ASC, "date")))
            .willReturn(Arrays.asList(_performance1, _performance2));

        MilestoneFacet facet = milestoneFacetBuilder(null).build();

        _milestoneService.initialiseMilestoneFacet(facet);

        MilestoneFacet expectedFacet = milestoneFacetBuilder(null).setInitialCount(2).build();
        expectedFacet.addMilestone(1, _performance1);

        verify(_milestoneRepository).save(Collections.singletonList(new Milestone(1, _performance1, new HashMap<>())));
        verify(_milestoneFacetRepository).save(expectedFacet);
    }

}
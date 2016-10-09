package com.mills.performances.repositories;

import com.mills.performances.AbstractIntegrationTest;
import com.mills.performances.models.AuthUser;
import com.mills.performances.models.Milestone;
import com.mills.performances.models.Performance;
import com.mills.performances.services.MilestoneService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.mills.performances.builders.PerformanceBuilder.tritonDelightPerformance;
import static com.mills.performances.builders.PerformanceBuilder.yorkshireMajorPerformance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by ryan on 09/10/16.
 */
public class MilestoneRepositoryTest extends AbstractIntegrationTest {

    @Test
    public void cannotFindMilestoneForAnotherUser() {
        Performance performance = yorkshireMajorPerformance().build();
        _performanceRepository.save(performance);

        Milestone milestone1 = new Milestone(1, performance, new HashMap<>());

        AuthUser user2 = new AuthUser();
        _authUserRepository.save(user2);

        Milestone milestone2 = new Milestone(5, performance, new HashMap<>());
        milestone2.setCustomer(user2);

        _milestoneRepository.save(Arrays.asList(milestone1, milestone2));

        List<Milestone> milestoneList = _milestoneRepository.findAll();
        assertThat(milestoneList, hasSize(1));
    }

}

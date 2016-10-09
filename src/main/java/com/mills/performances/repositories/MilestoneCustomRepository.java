package com.mills.performances.repositories;

import com.mills.performances.models.Milestone;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface MilestoneCustomRepository {

    List<Milestone> findAll(Sort sort);

}

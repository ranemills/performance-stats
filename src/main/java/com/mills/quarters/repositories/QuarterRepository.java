package com.mills.quarters.repositories;

import com.mills.quarters.models.Quarter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface QuarterRepository extends JpaRepository<Quarter, Long> {

    List<Quarter> findAll();

}

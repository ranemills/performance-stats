package com.mills.performances.repositories;

import com.mills.performances.models.Performance;
import com.mills.performances.models.temp.DateTempCount;
import com.mills.performances.models.temp.IntegerTempCount;
import com.mills.performances.models.temp.PerformanceSearchOptions;
import com.mills.performances.models.temp.StringTempCount;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface PerformanceCustomRepository {

    List<Performance> findPerformances(PerformanceSearchOptions searchOptions);

    List<Performance> findPerformances(PerformanceSearchOptions searchOptions, Sort sort);

    List<StringTempCount> findMethodCounts(PerformanceSearchOptions searchOptions);

    List<StringTempCount> findStageCounts(PerformanceSearchOptions searchOptions);

    List<DateTempCount> findDateCounts(PerformanceSearchOptions searchOptions);

    List<IntegerTempCount> findYearCounts(PerformanceSearchOptions searchOptions);

    List<StringTempCount> findRingerCounts(PerformanceSearchOptions searchOptions);

}

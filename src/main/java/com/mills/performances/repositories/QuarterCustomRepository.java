package com.mills.performances.repositories;

import com.mills.performances.models.Performance;
import com.mills.performances.models.temp.DateTempCount;
import com.mills.performances.models.temp.QuarterSearchOptions;
import com.mills.performances.models.temp.StringTempCount;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface QuarterCustomRepository {

    List<Performance> findQuarters(QuarterSearchOptions searchOptions);

    List<StringTempCount> findMethodCounts(QuarterSearchOptions searchOptions);

    List<StringTempCount> findStageCounts(QuarterSearchOptions searchOptions);

    List<DateTempCount> findDateCounts(QuarterSearchOptions searchOptions);

    List<StringTempCount> findRingerCounts(QuarterSearchOptions searchOptions);

}

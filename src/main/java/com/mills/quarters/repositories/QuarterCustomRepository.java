package com.mills.quarters.repositories;

import com.mills.quarters.models.Quarter;
import com.mills.quarters.models.temp.DateTempCount;
import com.mills.quarters.models.temp.QuarterSearchOptions;
import com.mills.quarters.models.temp.StringTempCount;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ryan on 12/04/16.
 */
@Repository
public interface QuarterCustomRepository {

    List<Quarter> findQuarters(QuarterSearchOptions searchOptions);

    List<StringTempCount> findMethodCounts(QuarterSearchOptions searchOptions);

    List<StringTempCount> findStageCounts(QuarterSearchOptions searchOptions);

    List<DateTempCount> findDateCounts(QuarterSearchOptions searchOptions);

    List<StringTempCount> findRingerCounts(QuarterSearchOptions searchOptions);

}

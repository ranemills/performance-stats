package com.mills.performances.services.impl;

import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.Performance;
import com.mills.performances.models.temp.PerformanceSearchOptions;
import com.mills.performances.repositories.PerformanceRepository;
import com.mills.performances.services.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ryanmills on 16/09/2016.
 */
@Service
public class PerformanceServiceImpl implements PerformanceService {

    @Autowired
    PerformanceRepository _performanceRepository;

    @Override
    public Boolean propertiesMatch(Performance performance, PerformanceProperty property, Object value) {
        switch (property) {
            case STAGE:
                return performance.getStage().equals(value);
            case METHOD:
                return performance.getMethod().equals(value);
        }
        throw new IllegalArgumentException("No handling for property in Performance" + property);
    }

    @Override
    public List<Performance> findByProperties(Map<PerformanceProperty, Object> properties, Sort sort) {
        return _performanceRepository.findPerformances(
            PerformanceSearchOptions.fromPerformanceProperties(properties),
            sort);
    }
}

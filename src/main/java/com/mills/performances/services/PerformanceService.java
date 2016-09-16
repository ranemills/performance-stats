package com.mills.performances.services;

import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.Performance;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 * Created by ryanmills on 16/09/2016.
 */
public interface PerformanceService {
    Boolean propertiesMatch(Performance performance, PerformanceProperty property, Object value);

    List<Performance> findByProperties(Map<PerformanceProperty, Object> properties, Sort sort);
}

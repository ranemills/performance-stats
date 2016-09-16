package com.mills.performances.services.impl;

import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.Performance;
import com.mills.performances.services.PerformanceService;
import org.springframework.stereotype.Service;

/**
 * Created by ryanmills on 16/09/2016.
 */
@Service
public class PerformanceServiceImpl implements PerformanceService {

    @Override
    public Boolean propertiesMatch(Performance performance, PerformanceProperty property, Object value) {
        switch(property) {
            case STAGE:
                return performance.getStage().equals(value);
            case LOCATION:
                return performance.getLocation().equals(value);
            case METHOD:
                return performance.getMethod().equals(value);
        }
        throw new IllegalArgumentException("No handling for property in Performance" + property);
    }
}

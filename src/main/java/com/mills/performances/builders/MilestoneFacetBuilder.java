package com.mills.performances.builders;

import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.MilestoneFacet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ryan on 15/09/16.
 */
public class MilestoneFacetBuilder {

    private Map<PerformanceProperty, Object> _properties;

    private MilestoneFacetBuilder() {
        _properties = new HashMap<>();
    }

    public static MilestoneFacetBuilder milestoneFacetBuilder() {
        return new MilestoneFacetBuilder();
    }

    public MilestoneFacetBuilder addPropertyValue(PerformanceProperty property, Object value)
    {
        _properties.put(property, value);
        return this;
    }

    public MilestoneFacet build() {
        return new MilestoneFacet(_properties);
    }

}

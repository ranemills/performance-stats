package com.mills.performances.builders;

import com.mills.performances.enums.MilestoneFacetType;
import com.mills.performances.enums.PerformanceProperty;
import com.mills.performances.models.BellBoardImport;
import com.mills.performances.models.MilestoneFacet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ryan on 15/09/16.
 */
public class MilestoneFacetBuilder {

    private Map<PerformanceProperty, Object> _properties;
    private Integer _count;
    private BellBoardImport _bellBoardImport;
    private MilestoneFacetType _type = MilestoneFacetType.COUNT;

    private MilestoneFacetBuilder(BellBoardImport bellBoardImport) {
        _properties = new HashMap<>();
        _bellBoardImport = bellBoardImport;
    }

    public static MilestoneFacetBuilder milestoneFacetBuilder(BellBoardImport bellBoardImport) {
        return new MilestoneFacetBuilder(bellBoardImport);
    }

    public MilestoneFacetBuilder addPropertyValue(PerformanceProperty property, Object value)
    {
        _properties.put(property, value);
        return this;
    }

    public MilestoneFacetBuilder setInitialCount(Integer count)
    {
        _count = count;
        return this;
    }

    public MilestoneFacetBuilder setType(MilestoneFacetType type)
    {
        _type = type;
        return this;
    }

    public MilestoneFacet build() {
        if(_count != null) {
            return new MilestoneFacet(_bellBoardImport, _properties, _type, _count);
        }
        else {
            return new MilestoneFacet(_bellBoardImport, _properties, _type);
        }
    }

}

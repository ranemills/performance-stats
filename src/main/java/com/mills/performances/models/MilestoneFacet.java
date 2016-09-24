package com.mills.performances.models;

import com.mills.performances.enums.MilestoneFacetType;
import com.mills.performances.enums.PerformanceProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mills.performances.MongoConfiguration.DOCUMENT_MILESTONE_FACETS;

/**
 * Created by ryan on 15/09/16.
 */
@Document(collection = DOCUMENT_MILESTONE_FACETS)
final public class MilestoneFacet extends AbstractMongoModel {

    private final Map<PerformanceProperty, Object> _properties;
    private Integer _value;
    @DBRef
    private List<Milestone> _milestones;
    @DBRef
    private BellBoardImport _bellBoardImport;
    private final MilestoneFacetType _type;

    private MilestoneFacet() {
        _properties = new HashMap<>();
        _type = MilestoneFacetType.COUNT;
    }

    public MilestoneFacet(BellBoardImport bellBoardImport, Map<PerformanceProperty, Object> properties,
                          MilestoneFacetType type) {
        _properties = properties;
        _value = 0;
        _milestones = new ArrayList<>();
        _bellBoardImport = bellBoardImport;
        _type = type;
    }

    public MilestoneFacet(BellBoardImport bellBoardImport, Map<PerformanceProperty, Object> properties, MilestoneFacetType type, Integer initialCount) {
        this(bellBoardImport, properties, type);
        _value = initialCount;
    }

    public void addMilestone(Integer milestone, Performance performance)
    {
        _milestones.add(new Milestone(milestone, performance, _properties));
    }

    public MilestoneFacetType getType() {
        return _type;
    }

    public void incrementCount()
    {
        _value = _value + 1;
    }

    public void addValue(Integer value)
    {
        _value = _value + value;
    }

    public Map<PerformanceProperty, Object> getProperties() {
        return _properties;
    }

    public int getValue() {
        return _value;
    }

    public List<Milestone> getMilestones() {
        return _milestones;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("properties", _properties)
            .append("count", _value)
            .append("milestones", _milestones)
            .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        MilestoneFacet rhs = (MilestoneFacet) obj;
        return new EqualsBuilder()
                   .append(getProperties(), rhs.getProperties())
                   .append(getValue(), rhs.getValue())
                   .append(getMilestones(), rhs.getMilestones())
                   .isEquals();
    }
}

package com.mills.performances.models;

import com.mills.performances.enums.PerformanceProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mills.performances.MongoConfiguration.DOCUMENT_MILESTONES;

/**
 * Created by ryan on 15/09/16.
 */
@Document(collection = DOCUMENT_MILESTONES)
final public class MilestoneFacet extends AbstractMongoModel {

    final private Map<PerformanceProperty, Object> _properties;
    private Integer _count;
    private List<Milestone> _milestones;

    private MilestoneFacet() {
        _properties = new HashMap<>();
    }

    public MilestoneFacet(Map<PerformanceProperty, Object> properties) {
        _properties = properties;
        _count = 0;
        _milestones = new ArrayList<>();
    }

    public void addMilestone(Integer milestone, Performance performance)
    {
        _milestones.add(new Milestone(milestone, performance));
    }

    public void incrementCount()
    {
        _count = _count + 1;
    }

    public Map<PerformanceProperty, Object> getProperties() {
        return _properties;
    }

    public int getCount() {
        return _count;
    }

    final class Milestone {
        private final Integer _milestone;
        @DBRef
        private final Performance _performance;

        public Milestone(Integer milestone, Performance performance) {
            _milestone = milestone;
            _performance = performance;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("properties", _properties)
            .append("count", _count)
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
        Performance rhs = (Performance) obj;
        return new EqualsBuilder()
                   .append(getProperties(), getProperties())
                   .append(getCount(), getCount())
                   .isEquals();
    }
}

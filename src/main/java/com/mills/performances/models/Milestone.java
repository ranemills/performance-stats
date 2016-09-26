package com.mills.performances.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mills.performances.enums.PerformanceProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

import static com.mills.performances.MongoConfiguration.DOCUMENT_MILESTONES;

/**
 * Created by ryan on 18/09/16.
 */
@Document(collection = DOCUMENT_MILESTONES)
public final class Milestone extends AbstractMongoModel {
    private final Integer _milestone;
    private final Date _date;
    @DBRef
    private final Performance _performance;
    private final Map<PerformanceProperty, Object> _properties;

    private Milestone() {
        _milestone = null;
        _date = null;
        _performance = null;
        _properties = null;
    }

    public Milestone(Integer milestone, Performance performance, Map<PerformanceProperty, Object> properties) {
        _milestone = milestone;
        _performance = performance;
        _date = _performance.getDate();
        _properties = properties;
    }

    public Map<PerformanceProperty, Object> getProperties() {
        return _properties;
    }

    public Integer getMilestoneValue() {
        return _milestone;
    }

    public Performance getPerformance() {
        return _performance;
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
        Milestone rhs = (Milestone) obj;
        return new EqualsBuilder()
                   .append(getMilestoneValue(), rhs.getMilestoneValue())
                   .append(getPerformance(), rhs.getPerformance())
                   .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("_milestone", _milestone)
                                        .append("_performance", _performance)
                                        .build();
    }
}

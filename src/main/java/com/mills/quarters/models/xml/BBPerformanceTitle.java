package com.mills.quarters.models.xml;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.simpleframework.xml.Element;

public class BBPerformanceTitle {
    @Element
    public Integer changes;

    @Element
    public String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getChanges() {
        return changes;
    }

    public void setChanges(Integer changes) {
        this.changes = changes;
    }

    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        BBPerformanceTitle rhs = (BBPerformanceTitle) obj;
        return new EqualsBuilder()
                   .append(getMethod(), rhs.getMethod())
                   .append(getChanges(), rhs.getChanges())
                   .isEquals();
    }
}
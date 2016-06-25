package com.mills.bellboard.models.xml;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(strict = false)
public class BBPerformanceRinger {

    @Text
    private String name;
    @Attribute
    private Integer bell;
    @Attribute(required = false)
    private Boolean conductor;

    public BBPerformanceRinger() {

    }

    public BBPerformanceRinger(String name, int bell, boolean conductor) {
        this.name = name;
        this.bell = bell;
        this.conductor = conductor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBell() {
        return bell;
    }

    public void setBell(Integer bell) {
        this.bell = bell;
    }

    public Boolean getConductor() {
        if (conductor == null) return false;
        return conductor;
    }

    public void setConductor(Boolean conductor) {
        this.conductor = conductor;
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
        BBPerformanceRinger rhs = (BBPerformanceRinger) obj;
        return new EqualsBuilder()
                   .append(getName(), rhs.getName())
                   .append(getBell(), rhs.getBell())
                   .append(getConductor(), rhs.getConductor())
                   .isEquals();
    }

    @Override
    public String toString() {
        return name;
    }
}

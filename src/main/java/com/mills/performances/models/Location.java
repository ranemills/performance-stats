package com.mills.performances.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ryan on 14/04/16.
 */
public class Location extends AbstractMongoModel {

    private String dedication;
    private String county;
    private String town;

    public String getDedication() {
        return dedication;
    }

    public void setDedication(String dedication) {
        this.dedication = dedication;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
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
        Location rhs = (Location) obj;
        return new EqualsBuilder()
                   .append(getDedication(), rhs.getDedication())
                   .append(getTown(), rhs.getTown())
                   .append(getCounty(), rhs.getCounty())
                   .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("dedication", getDedication())
                                        .append("town", getTown())
                                        .append("county", getCounty())
                                        .build();
    }
}

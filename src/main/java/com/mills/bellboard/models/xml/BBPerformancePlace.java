package com.mills.bellboard.models.xml;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.Map;

@Root(strict = false)
public class BBPerformancePlace {
    @ElementMap(entry = "place-name", key = "type", attribute = true, inline = true)
    private Map<String, String> placeElements;

    public void setPlaceElements(Map<String, String> placeElements) {
        this.placeElements = placeElements;
    }

    public String getPlace() {
        return placeElements.get("place");
    }

    public String getDedication() {
        return placeElements.get("dedication");
    }

    public String getCounty() {
        return placeElements.get("county");
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
        BBPerformancePlace rhs = (BBPerformancePlace) obj;
        return new EqualsBuilder()
                   .append(getPlace(), rhs.getPlace())
                   .append(getDedication(), rhs.getDedication())
                   .append(getCounty(), rhs.getCounty())
                   .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("place", getPlace())
                                        .append("dedication", getDedication())
                                        .append("county", getCounty())
                                        .build();
    }

}

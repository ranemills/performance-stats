package com.mills.quarters.models.xml;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.Map;

@Root(strict = false)
public class BBPerformancePlace {
    @ElementMap(entry = "place-name", key = "type", attribute = true, inline = true)
    public Map<String, String> placeElements;

    public void setPlaceElements(Map<String, String> placeElements) {
        this.placeElements = placeElements;
    }

    public Map<String, String> getPlaceElement() {
        return placeElements;
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

    public String toString() {
        return placeElements.get("place") + placeElements.get("dedication") + placeElements.get("county");
    }

    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        BBPerformancePlace rhs = (BBPerformancePlace) obj;
        return new EqualsBuilder()
                .append(getPlaceElement(), rhs.getPlaceElement())
                .isEquals();
    }

}
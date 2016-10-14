package com.mills.performances.models.temp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mills.performances.models.Location;
import com.mills.performances.models.temp.serializers.LocationTempCountSerializer;

import java.util.HashMap;

/**
 * Created by ryan on 19/04/16.
 */
@JsonSerialize(using = LocationTempCountSerializer.class)
public class LocationTempCount extends TempCount {
    public LocationTempCount(Object property, int count) {
        super(property, count);
    }

    @JsonIgnore(false)
    public Location getProperty()
    {
        HashMap<String, Object> property = (HashMap<String, Object>) super.getProperty();
        Location location = new Location();
        location.setDedication((String) property.get("dedication"));
        location.setTown((String) property.get("town"));
        location.setCounty((String) property.get("county"));
        return location;
    }

}

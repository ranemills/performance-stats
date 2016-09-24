package com.mills.performances.models.temp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mills.performances.models.Location;

/**
 * Created by ryan on 19/04/16.
 */
public class LocationTempCount extends TempCount {
    public LocationTempCount(Object property, int count) {
        super(property, count);
    }

    @JsonIgnore(false)
    public Location getProperty()
    {
        return (Location) super.getProperty();
    }

}

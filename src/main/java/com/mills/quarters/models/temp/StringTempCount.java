package com.mills.quarters.models.temp;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by ryan on 19/04/16.
 */
public class StringTempCount extends TempCount {
    public StringTempCount(Object property, int count) {
        super(property, count);
    }

    @JsonIgnore(false)
    public String getProperty()
    {
        return (String) super.getProperty();
    }

}

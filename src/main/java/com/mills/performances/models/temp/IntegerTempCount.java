package com.mills.performances.models.temp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by ryan on 19/04/16.
 */
public class IntegerTempCount extends TempCount {
    public IntegerTempCount(Object property, int count) {
        super(property, count);
    }

    @JsonIgnore(false)
    public Integer getProperty()
    {
        return (Integer) super.getProperty();
    }

}

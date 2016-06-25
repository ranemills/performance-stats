package com.mills.performances.models.temp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by ryan on 19/04/16.
 */
public class DateTempCount extends TempCount {
    public DateTempCount(Object property, int count) {
        super(property, count);
    }

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonIgnore(false)
    public Date getProperty()
    {
        return (Date) super.getProperty();
    }

}

package com.mills.quarters.models.temp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by ryan on 19/04/16.
 */
public class TempCount {
    private int count;
    private Object property;

    public TempCount(Object property, int count) {
        this.property = property;
        this.count = count;
    }

    @JsonIgnore
    public Object getProperty() {
        return property;
    }

    public void setProperty(Object property) {
        this.property = property;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}

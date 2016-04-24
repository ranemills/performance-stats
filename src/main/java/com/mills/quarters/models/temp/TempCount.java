package com.mills.quarters.models.temp;

/**
 * Created by ryan on 19/04/16.
 */
public class TempCount {
    private int count;
    private String property;

    public TempCount(String property, int count) {
        this.property = property;
        this.count = count;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

}

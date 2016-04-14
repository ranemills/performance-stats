package com.mills.quarters.models;

/**
 * Created by ryan on 14/04/16.
 */
public class Location extends AbstractMongoModel {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

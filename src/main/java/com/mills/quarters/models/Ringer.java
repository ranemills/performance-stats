package com.mills.quarters.models;

/**
 * Created by ryan on 14/04/16.
 */
public class Ringer extends AbstractMongoModel {

    private Integer towerBell;
    private Integer methodBell;
    private String name;

    public Ringer(String name, Integer methodBell) {
        this.name = name;
        this.methodBell = methodBell;
    }

    public Integer getTowerBell() {
        return towerBell;
    }

    public Integer getMethodBell() {
        return methodBell;
    }

    public String getName() {
        return name;
    }
}

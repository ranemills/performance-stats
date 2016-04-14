package com.mills.quarters.models;

/**
 * Created by ryan on 14/04/16.
 */
public class Ringer {

    private Integer towerBell;
    private Integer methodBell;
    private String name;

    public Ringer(String name, int bell) {
        this.name = name;
        this.methodBell = bell;
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

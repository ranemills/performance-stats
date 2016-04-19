package com.mills.quarters.models;

/**
 * Created by ryan on 14/04/16.
 */
public class Ringer extends AbstractMongoModel {

    private Integer towerBell;
    private Integer methodBell;
    private String name;

    public Boolean getConductor() {
        return conductor;
    }

    private Boolean conductor;

    public Ringer() { }

    public Ringer(String name, Integer methodBell) {
        this.name = name;
        this.methodBell = methodBell;
    }

    public Ringer(String name, Integer methodBell, Boolean conductor) {
        this.name = name;
        this.methodBell = methodBell;
        this.conductor = conductor;
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

package com.mills.performances.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.json.JSONObject;

import static com.mills.performances.builders.JSONObjectBuilder.jsonObjectBuilder;

/**
 * Created by ryan on 14/04/16.
 */
public class Ringer extends AbstractMongoModel implements AlgoliaModel {

    private Integer towerBell;
    private Integer methodBell;
    private String name;
    private Boolean conductor;

    public Ringer() {
    }

    public Ringer(String name, Integer methodBell) {
        this.name = name;
        this.methodBell = methodBell;
    }

    public Ringer(String name, Integer methodBell, Boolean conductor) {
        this.name = name;
        this.methodBell = methodBell;
        this.conductor = conductor;
    }

    public Boolean getConductor() {
        return conductor;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Ringer rhs = (Ringer) obj;
        return new EqualsBuilder()
                   .append(getName(), rhs.getName())
                   .append(getMethodBell(), rhs.getMethodBell())
                   .append(getTowerBell(), rhs.getTowerBell())
                   .append(getConductor(), rhs.getConductor())
                   .isEquals();
    }

    @Override
    public String toString() {
        return "Name=" + getName() + ":towerBell=" + getTowerBell() + ":methodBell=" + getMethodBell() +
               ":Conductor=" + getConductor();
    }

    @Override
    public JSONObject toJSONObject() {
        return jsonObjectBuilder().put("name", getName())
                                  .build();
    }
}

package com.mills.quarters.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.mills.quarters.models.xml.BBPerformance;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Date;
import java.util.List;

/**
 * Created by ryan on 12/04/16.
 */
public class Quarter extends AbstractMongoModel {

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date date;
    private Integer changes;
    private String method;
    private String stage;
    private Location location;
    private List<Ringer> ringers;

    public List<Ringer> getRingers() {
        return ringers;
    }

    public void setRingers(List<Ringer> ringers) {
        this.ringers = ringers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getChanges() {
        return changes;
    }

    public void setChanges(Integer changes) {
        this.changes = changes;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Quarter rhs = (Quarter) obj;
        return new EqualsBuilder()
                   .append(getDate(), rhs.getDate())
                   .append(getChanges(), rhs.getChanges())
                   .append(getMethod(), rhs.getMethod())
                   .append(getStage(), rhs.getStage())
                   .append(getLocation(), rhs.getLocation())
                   .append(getRingers(), rhs.getRingers())
                   .isEquals();
    }

    @Override
    public String toString() {
        return "Date=" + getDate() + ":Location=" + getLocation() + ":Changes=" + getChanges() + ":Method=" + getMethod() + ":Stage=" + getStage() + ":Ringers" + getRingers();
    }
}

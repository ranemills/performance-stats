package com.mills.quarters.models;


import com.fasterxml.jackson.annotation.JsonFormat;

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
}

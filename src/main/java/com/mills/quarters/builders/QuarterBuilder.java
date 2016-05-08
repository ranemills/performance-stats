package com.mills.quarters.builders;

import com.mills.quarters.models.AuthUser;
import com.mills.quarters.models.Location;
import com.mills.quarters.models.Quarter;
import com.mills.quarters.models.Ringer;
import com.mills.quarters.services.MongoService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ryan on 14/04/16.
 */
public class QuarterBuilder {

    private Date date;
    private Integer changes;
    private String method;
    private String stage;
    private Location location;
    private List<Ringer> ringers;
    private AuthUser customer;
    private String bellboardId;

    private QuarterBuilder() {
        ringers = new ArrayList<>();
    }

    public static QuarterBuilder quarterBuilder() {
        return new QuarterBuilder();
    }

    public String getBellboardId() {
        return bellboardId;
    }

    public QuarterBuilder bellboardId(String bellboardId) {
        this.bellboardId = bellboardId;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public QuarterBuilder date(Date date) {
        this.date = date;
        return this;
    }

    public QuarterBuilder changes(int changes) {
        this.changes = changes;
        return this;
    }

    public QuarterBuilder method(String method) {
        this.method = method;
        return this;
    }

    public QuarterBuilder stage(String stage) {
        this.stage = stage;
        return this;
    }

    public QuarterBuilder location(Location location) {
        this.location = location;
        return this;
    }

    public QuarterBuilder location(String locationName) {
        this.location = new Location();
        this.location.setName(locationName);
        return this;
    }

    public QuarterBuilder ringer(Ringer ringer) {
        this.ringers.add(ringer);
        return this;
    }

    public QuarterBuilder ringer(int bell, String name) {
        this.ringers.add(new Ringer(name, bell));
        return this;
    }

    public QuarterBuilder ringer(int bell, String name, boolean conductor) {
        this.ringers.add(new Ringer(name, bell, conductor));
        return this;
    }

    public Quarter build() {
        Quarter quarter = new Quarter();
        quarter.setDate(date);
        quarter.setChanges(changes);
        quarter.setMethod(method);
        quarter.setStage(stage);
        quarter.setLocation(location);
        quarter.setRingers(ringers);
        quarter.setBellboardId(bellboardId);
        quarter = MongoService.setCustomer(quarter);

        return quarter;
    }


}

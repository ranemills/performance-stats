package com.mills.quarters.builders;

import com.mills.quarters.models.BellBoardImport;
import com.mills.quarters.models.Location;
import com.mills.quarters.models.Quarter;
import com.mills.quarters.models.Ringer;
import com.mills.quarters.services.MongoService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ryan on 14/04/16.
 */
public class QuarterBuilder {

    private Date _date;
    private Integer _changes;
    private String _method;
    private String _stage;
    private Location _location;
    private List<Ringer> _ringers;
    private String _bellboardId;
    private BellBoardImport _bellboardImport;

    private QuarterBuilder() {
        _ringers = new ArrayList<>();
    }

    public static QuarterBuilder quarterBuilder() {
        return new QuarterBuilder();
    }

    public QuarterBuilder bellboardImport(BellBoardImport bellboardImport) {
        this._bellboardImport = bellboardImport;
        return this;
    }

    public QuarterBuilder bellboardId(String bellboardId) {
        this._bellboardId = bellboardId;
        return this;
    }

    public QuarterBuilder date(Date date) {
        this._date = date;
        return this;
    }

    public QuarterBuilder changes(int changes) {
        this._changes = changes;
        return this;
    }

    public QuarterBuilder method(String method) {
        this._method = method;
        return this;
    }

    public QuarterBuilder stage(String stage) {
        this._stage = stage;
        return this;
    }

    public QuarterBuilder location(Location location) {
        this._location = location;
        return this;
    }

    public QuarterBuilder location(String locationName) {
        this._location = new Location();
        this._location.setName(locationName);
        return this;
    }

    public QuarterBuilder ringer(Ringer ringer) {
        this._ringers.add(ringer);
        return this;
    }

    public QuarterBuilder ringer(int bell, String name) {
        this._ringers.add(new Ringer(name, bell));
        return this;
    }

    public QuarterBuilder ringer(int bell, String name, boolean conductor) {
        this._ringers.add(new Ringer(name, bell, conductor));
        return this;
    }

    public Quarter build() {
        Quarter quarter = new Quarter();
        quarter.setDate(_date);
        quarter.setChanges(_changes);
        quarter.setMethod(_method);
        quarter.setStage(_stage);
        quarter.setLocation(_location);
        quarter.setRingers(_ringers);
        quarter.setBellboardId(_bellboardId);
        if(_bellboardImport != null)
        {
            quarter.setBellBoardImport(_bellboardImport);
        }
        quarter = MongoService.setCustomer(quarter);

        return quarter;
    }


}

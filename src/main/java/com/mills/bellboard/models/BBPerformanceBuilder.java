package com.mills.bellboard.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mills.bellboard.models.xml.BBPerformance;
import com.mills.bellboard.models.xml.BBPerformancePlace;
import com.mills.bellboard.models.xml.BBPerformanceRinger;
import com.mills.bellboard.models.xml.BBPerformanceTitle;

/**
 * Created by ryan on 12/07/16.
 */
public class BBPerformanceBuilder {
    private final ImmutableList.Builder<BBPerformanceRinger> _ringerBuilder = ImmutableList.builder();
    private final ImmutableMap.Builder<String, String> _placeElementsBuilder = ImmutableMap.builder();
    private String _id;
    private String _association;
    private String _date;
    private Integer _changes;
    private String _method;

    public static BBPerformanceBuilder bbPerformanceBuilder() {
        return new BBPerformanceBuilder();
    }

    public BBPerformanceBuilder id(String id) {
        this._id = id;
        return this;
    }

    public BBPerformanceBuilder association(String association) {
        this._association = association;
        return this;
    }

    public BBPerformanceBuilder date(String date) {
        this._date = date;
        return this;
    }

    public BBPerformanceBuilder place(String place) {
        _placeElementsBuilder.put("place", place);
        return this;
    }

    public BBPerformanceBuilder dedication(String dedication) {
        _placeElementsBuilder.put("dedication", dedication);
        return this;
    }

    public BBPerformanceBuilder county(String county) {
        _placeElementsBuilder.put("county", county);
        return this;
    }

    public BBPerformanceBuilder changes(Integer changes) {
        this._changes = changes;
        return this;
    }

    public BBPerformanceBuilder method(String method) {
        this._method = method;
        return this;
    }

    public BBPerformanceBuilder ringer(int bell, String name, Boolean conductor) {
        _ringerBuilder.add(new BBPerformanceRinger(name, bell, conductor));
        return this;
    }

    public BBPerformanceBuilder ringer(int bell, String name) {
        _ringerBuilder.add(new BBPerformanceRinger(name, bell, null));
        return this;
    }

    public BBPerformance build()
    {
        BBPerformance expectedPerformance = new BBPerformance();

        expectedPerformance.setBellboadId(_id);
        expectedPerformance.setAssociation(_association);
        expectedPerformance.setDate(_date);

        BBPerformancePlace performancePlace = new BBPerformancePlace();
        performancePlace.setPlaceElements(_placeElementsBuilder.build());
        expectedPerformance.setPlace(performancePlace);

        expectedPerformance.setRingers(_ringerBuilder.build());

        BBPerformanceTitle performanceTitle = new BBPerformanceTitle();
        performanceTitle.setChanges(_changes);
        performanceTitle.setMethod(_method);

        expectedPerformance.setTitle(performanceTitle);

        return expectedPerformance;
    }
}

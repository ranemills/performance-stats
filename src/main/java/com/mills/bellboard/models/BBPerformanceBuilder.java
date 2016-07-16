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

    public static BBPerformanceBuilder tritonDelightBbPerformance() {
        return bbPerformanceBuilder().id("1500")
                                     .association("Oxford Society")
                                     .place("Oxford")
                                     .dedication("Christ Church")
                                     .county("Oxfordshire")
                                     .date("2016-03-21")
                                     .changes(1440)
                                     .method("Triton Delight Royal")
                                     .ringer(1, "Bernard J Stone")
                                     .ringer(2, "Robin O Hall", true)
                                     .ringer(3, "Michele Winter")
                                     .ringer(4, "Ryan E Mills")
                                     .ringer(5, "Stephen M Jones")
                                     .ringer(6, "Stuart F Gibson")
                                     .ringer(7, "Elizabeth C Frye")
                                     .ringer(8, "Michael A Williams")
                                     .ringer(9, "Mark D Tarrant")
                                     .ringer(10, "Colin M Lee");
    }

    public static BBPerformanceBuilder yorkshireMajorBbPerformance() {
        return bbPerformanceBuilder().id("101")
                                     .association("")
                                     .place("Abingdon")
                                     .dedication("St Helen")
                                     .county("Oxfordshire")
                                     .date("2016-04-10")
                                     .changes(1280)
                                     .method("Yorkshire Surprise Major")
                                     .ringer(1, "Rebecca Franklin")
                                     .ringer(2, "Brian Read")
                                     .ringer(3, "Susan Read")
                                     .ringer(4, "Sarah Barnes")
                                     .ringer(5, "David Thomas", true)
                                     .ringer(6, "Matthew Franklin")
                                     .ringer(7, "Tim Pett")
                                     .ringer(8, "Ryan Mills");
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

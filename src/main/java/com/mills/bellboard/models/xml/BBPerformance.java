package com.mills.bellboard.models.xml;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "performance", strict = false)
public class BBPerformance {

    @Attribute(name = "id")
    private String bellboadId;
    @Element(required = false)
    private String association;
    @Element
    private String date;
    @Element
    private BBPerformanceTitle title;
    @Element
    private BBPerformancePlace place;
    @Element(required=false)
    private String duration;
    @ElementList
    private List<BBPerformanceRinger> ringers;

    public String getDuration() {
        return duration;
    }

    public String getBellboadId() {
        return bellboadId;
    }

    public void setBellboadId(String bellboadId) {
        this.bellboadId = bellboadId;
    }

    private String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BBPerformanceTitle getTitle() {
        return title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setTitle(BBPerformanceTitle title) {
        this.title = title;
    }

    public List<BBPerformanceRinger> getRingers() {
        return ringers;
    }

    public void setRingers(List<BBPerformanceRinger> ringers) {
        this.ringers = ringers;
    }

    public BBPerformancePlace getPlace() {
        return place;
    }

    public void setPlace(BBPerformancePlace place) {
        this.place = place;
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
        BBPerformance rhs = (BBPerformance) obj;
        return new EqualsBuilder()
                   .append(getAssociation(), rhs.getAssociation())
                   .append(getDate(), rhs.getDate())
                   .append(getPlace(), rhs.getPlace())
                   .append(getRingers(), rhs.getRingers())
                   .append(getTitle(), rhs.getTitle())
                   .append(getDuration(), rhs.getDuration())
                   .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(date)
                                        .append("association", association)
                                        .append("date", date)
                                        .append("place", place)
                                        .append("ringers", ringers)
                                        .append("title", title)
                                        .append("duration", duration)
                                        .build();
    }
}



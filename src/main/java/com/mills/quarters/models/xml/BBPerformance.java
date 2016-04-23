package com.mills.quarters.models.xml;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "performance", strict = false)
public class BBPerformance {

    @Element(required=false)
    String association;

    @Element
    String date;

    @Element
    BBPerformanceTitle title;
    @Element
    BBPerformancePlace place;

    @ElementList
    ArrayList<BBPerformanceRinger> ringers;

    public String getAssociation() {
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

    public void setTitle(BBPerformanceTitle title) {
        this.title = title;
    }

    public ArrayList<BBPerformanceRinger> getRingers() {
        return ringers;
    }

    public BBPerformancePlace getPlace() {
        return place;
    }

    public String toString() {
        return "Date: " + date + ", Title: " + title + ", Place: " + place + ", Ringers: " + ringers;
    }

    public void setPlace(BBPerformancePlace place) {
        this.place = place;
    }

    public void setRingers(ArrayList<BBPerformanceRinger> ringers) {
        this.ringers = ringers;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
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
                   .isEquals();
    }
}



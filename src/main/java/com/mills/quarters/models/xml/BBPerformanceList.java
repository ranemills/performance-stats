package com.mills.quarters.models.xml;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "performances", strict = false)
public class BBPerformanceList {

    @ElementList(entry = "performance", inline = true)
    private ArrayList<BBPerformance> performances;

    public ArrayList<BBPerformance> getPerformances() {
        return performances;
    }

    public void setPerformances(ArrayList<BBPerformance> performances) {
        this.performances = performances;
    }

}

package com.mills.performances.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import com.mills.performances.enums.PerformanceProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.mills.performances.MongoConfiguration.DOCUMENT_PERFORMANCE;
import static javax.swing.text.html.HTML.Tag.P;

/**
 * Created by ryan on 12/04/16.
 */
@Document(collection = DOCUMENT_PERFORMANCE)
public final class Performance extends AbstractMongoModel {

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private Integer changes;
    private String method;
    private String stage;
    private Location location;
    private List<Ringer> ringers;
    private String bellboardId;
    private Integer time;
    private Integer year;
    @DBRef
    private BellBoardImport bellBoardImport;

    public Performance()
    {
        super();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public BellBoardImport getBellBoardImport() {
        return bellBoardImport;
    }

    public void setBellBoardImport(BellBoardImport bellBoardImport) {
        this.bellBoardImport = bellBoardImport;
    }

    public String getBellboardId() {
        return bellboardId;
    }

    public void setBellboardId(String bellboardId) {
        this.bellboardId = bellboardId;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Performance that = (Performance) o;

        return new EqualsBuilder()
                   .append(getId(), that.getId())
                   .append(getCustomer(), that.getCustomer())
                   .append(date, that.date)
                   .append(changes, that.changes)
                   .append(method, that.method)
                   .append(stage, that.stage)
                   .append(location, that.location)
                   .append(ringers, that.ringers)
                   .append(bellboardId, that.bellboardId)
                   .append(time, that.time)
                   .append(year, that.year)
                   .append(bellBoardImport, that.bellBoardImport)
                   .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                   .append(getId())
                   .append(getCustomer())
                   .append(date)
                   .append(changes)
                   .append(method)
                   .append(stage)
                   .append(location)
                   .append(ringers)
                   .append(bellboardId)
                   .append(time)
                   .append(year)
                   .append(bellBoardImport)
                   .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("_id", getId())
                          .add("_customer", getCustomer())
                          .add("date", date)
                          .add("changes", changes)
                          .add("method", method)
                          .add("stage", stage)
                          .add("location", location)
                          .add("ringers", ringers)
                          .add("bellboardId", bellboardId)
                          .add("time", time)
                          .add("year", year)
                          .add("bellBoardImport", bellBoardImport)
                          .toString();
    }
}

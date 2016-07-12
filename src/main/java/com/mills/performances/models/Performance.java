package com.mills.performances.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

import static com.mills.performances.MongoConfiguration.DOCUMENT_PERFORMANCE;
import static com.mills.performances.builders.JSONObjectBuilder.jsonObjectBuilder;

/**
 * Created by ryan on 12/04/16.
 */
@Document(collection = DOCUMENT_PERFORMANCE)
public class Performance extends AbstractMongoModel implements AlgoliaModel {

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private Integer changes;
    private String method;
    private String stage;
    private Location location;
    private List<Ringer> ringers;
    private String bellboardId;
    @DBRef
    private BellBoardImport bellBoardImport;

    public Performance()
    {
        super();
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
        Performance rhs = (Performance) obj;
        return new EqualsBuilder()
                   .append(getBellboardId(), rhs.getBellboardId())
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
        return new ToStringBuilder(this).append("date", date)
            .append("location", location)
            .append("changes", changes)
            .append("method", method)
            .append("stage", stage)
            .append("ringers", ringers)
            .build();
    }

    @Override
    public JSONObject toJSONObject() {
        return jsonObjectBuilder().id(getId())
                                  .put("customer_id", getCustomer().getId())
                                  .put("date", getDate())
                                  .put("method", getMethod())
                                  .put("stage", getStage())
                                  .put("changes", getChanges())
                                  .put("location", getLocation())
                                  .put("ringers", ringers)
                                  .build();
    }

//    @Override
//    public Performance fromJSONObject(JSONObject jsonObject) {
//        id(new ObjectId((String) jsonObject.get("id")));
//        setDate(new Date((String) jsonObject.get("date")));
//        setMethod((String) jsonObject.get("method"));
//        setStage((String) jsonObject.get("stage"));
//        setChanges((Integer) jsonObject.get("changes"));
//        setLocation(new Location().fromJSONObject((JSONObject) jsonObject.get("location")));
//        JSONArray jsonRingers = (JSONArray) jsonObject.get("ringers");
//        List<Ringer> ringers = new ArrayList<>();
//        for (int i = 0; i < jsonRingers.length(); i++) {
//            ringers.add(new Ringer().fromJSONObject((JSONObject) jsonRingers.get(i));
//        }
//        setRingers(ringers);
//    }
}

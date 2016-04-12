package com.mills.quarters.models;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Created by ryan on 12/04/16.
 */
public class Quarter {

    @Id
    private ObjectId id;

    private Integer changes;

    private String method;

    private String stage;

    protected Quarter() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public Integer getChanges() {
        return changes;
    }

    public String getMethod() {
        return method;
    }

    public String getStage() {
        return stage;
    }

    public Quarter(Integer changes, String method, String stage) {
        this.changes = changes;
        this.method = method;
        this.stage = stage;
    }
}

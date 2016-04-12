package com.mills.quarters.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by ryan on 12/04/16.
 */
@Entity
public class Quarter {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer changes;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
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

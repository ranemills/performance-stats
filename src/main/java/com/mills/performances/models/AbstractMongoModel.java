package com.mills.performances.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import static com.mills.performances.utils.CustomerUtils.getCurrentUser;

/**
 * Created by ryan on 14/04/16.
 */
public abstract class AbstractMongoModel {

    @Id
    private ObjectId id;
    @JsonIgnore
    @DBRef
    private AuthUser customer;

    AbstractMongoModel()
    {
        customer = getCurrentUser();
    }


    public AuthUser getCustomer() {
        return customer;
    }

    public void setCustomer(AuthUser customer) {
        this.customer = customer;
    }

    public ObjectId getId() {
        return id;
    }

    public AbstractMongoModel id(ObjectId id) {
        this.id = id;
        return this;
    }
}

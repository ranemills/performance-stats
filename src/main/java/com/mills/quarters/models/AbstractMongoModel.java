package com.mills.quarters.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.DBRef;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Created by ryan on 14/04/16.
 */
abstract class AbstractMongoModel {

    @Id
    private ObjectId id;
    @JsonIgnore
    private DBRef customer;

    public DBRef getCustomer() {
        return customer;
    }

    public void setCustomer(DBRef customer) {
        this.customer = customer;
    }

    public void setCustomer(AuthUser customer) {
        this.customer = new DBRef("authUser", customer.getId());
    }

    public ObjectId getId() {
        return id;
    }

    public AbstractMongoModel id(ObjectId id) {
        this.id = id;
        return this;
    }
}

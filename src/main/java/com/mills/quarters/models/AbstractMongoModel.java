package com.mills.quarters.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Created by ryan on 14/04/16.
 */
public abstract class AbstractMongoModel {

    @Id
    ObjectId id;

}

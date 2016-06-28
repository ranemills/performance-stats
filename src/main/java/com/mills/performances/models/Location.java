package com.mills.performances.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.json.JSONObject;

import static com.mills.performances.builders.JSONObjectBuilder.jsonObjectBuilder;

/**
 * Created by ryan on 14/04/16.
 */
public class Location extends AbstractMongoModel implements AlgoliaModel {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Location rhs = (Location) obj;
        return new EqualsBuilder()
                   .append(getName(), rhs.getName())
                   .isEquals();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public JSONObject toJSONObject() {
        return jsonObjectBuilder().put("name", getName())
                                  .build();
    }
}

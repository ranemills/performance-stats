package com.mills.performances.builders;

import com.mills.performances.models.AlgoliaModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

/**
 * Created by ryan on 28/06/16.
 */
public class JSONObjectBuilder {

    private JSONObject result;

    private JSONObjectBuilder() {
        result = new JSONObject();
    }

    public static JSONObjectBuilder jsonObjectBuilder() {
        return new JSONObjectBuilder();
    }

    public <T> JSONObjectBuilder put(String key, Collection<T> collection)
    {
        JSONArray array = new JSONArray();
        for (T object : collection) {
            if (object instanceof AlgoliaModel) {
                array.put(((AlgoliaModel) object).toJSONObject());
            } else {
                array.put(object.toString());
            }
        }
        result.put(key, array);
        return this;
    }

    public JSONObjectBuilder put(String key, Object value)
    {
        if (value instanceof AlgoliaModel) {
            result.put(key, ((AlgoliaModel) value).toJSONObject());
        } else {
            result.put(key, value.toString());
        }
        return this;
    }

    public JSONObjectBuilder id(Object value)
    {
        return this.put("objectID", value);
    }

    public JSONObject build()
    {
        return result;
    }

}

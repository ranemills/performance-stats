package com.mills.performances.models.temp.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mills.performances.models.Location;
import com.mills.performances.models.temp.LocationTempCount;

import java.io.IOException;

/**
 * Created by ryanmills on 10/10/2016.
 */
public class LocationTempCountSerializer extends JsonSerializer<LocationTempCount> {

    @Override
    public void serialize(LocationTempCount locationTempCount, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Location location = locationTempCount.getProperty();
        String locationString = location.getDedication() + ", " + location.getTown() + ", " + location.getCounty();

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("count", locationTempCount.getCount());
        jsonGenerator.writeStringField("property", locationString);
        jsonGenerator.writeEndObject();
    }
}

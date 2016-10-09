package com.mills.performances.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by ryan on 15/09/16.
 */
public enum PerformanceProperty {
    METHOD,
    STAGE,
    RINGER,
    LOCATION;

    @Override
    public String toString() {
        return StringUtils.upperCase(name());
    }

    public static PerformanceProperty fromString(String value) {
        return Enum.valueOf(PerformanceProperty.class, value.toUpperCase());
    }
}

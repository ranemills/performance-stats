package com.mills.performances.enums;

import com.mills.performances.services.impl.MilestoneServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ryan on 16/09/16.
 */
public enum MilestoneValue {
    ONE(1),
    FIVE(5),
    TEN(10),
    TWENTY_FIVE(25),
    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    THREE_HUNDRED(300),
    FOUR_HUNDRED(400),
    FIVE_HUNDRED(500),
    NONE(-1);

    private static final Map<Integer, MilestoneValue> intToTypeMap = new HashMap<Integer, MilestoneValue>();

    static {
        for (MilestoneValue type : MilestoneValue.values()) {
            intToTypeMap.put(type._value, type);
        }
    }

    public static MilestoneValue fromInt(Integer i) {
        MilestoneValue type = intToTypeMap.get(i);
        if (type == null)
            return MilestoneValue.NONE;
        return type;
    }

    private Integer _value;

    MilestoneValue(Integer value) {
        _value = value;
    }

    public Integer getValue() {
        return _value;
    }


}

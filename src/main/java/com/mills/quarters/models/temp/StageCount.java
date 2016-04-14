package com.mills.quarters.models.temp;

/**
 * Created by ryan on 14/04/16.
 */
public class StageCount {

    private String stage;
    private Integer count;

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getStage() {

        return stage;
    }

    public Integer getCount() {
        return count;
    }
}

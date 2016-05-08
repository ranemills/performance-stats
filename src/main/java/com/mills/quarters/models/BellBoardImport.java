package com.mills.quarters.models;

/**
 * Created by ryan on 08/05/16.
 */
public class BellBoardImport extends AbstractMongoModel {

    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

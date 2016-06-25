package com.mills.performances.models;

import java.util.Date;

/**
 * Created by ryan on 08/05/16.
 */
public class BellBoardImport extends AbstractMongoModel {

    private String name;
    private String url;
    private Date lastImport;

    private BellBoardImport()
    {

    }

    public BellBoardImport(String url)
    {
        this.url = url;
    }

    public Date getLastImport() {
        return lastImport;
    }

    public void setLastImport(Date lastImport) {
        this.lastImport = lastImport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

}

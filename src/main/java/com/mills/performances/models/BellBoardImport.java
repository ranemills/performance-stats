package com.mills.performances.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

import static com.mills.performances.MongoConfiguration.DOCUMENT_BELLBOARDIMPORT;

/**
 * Created by ryan on 08/05/16.
 */
@Document(collection = DOCUMENT_BELLBOARDIMPORT)
public class BellBoardImport extends AbstractMongoModel {

    private String name;
    private String url;
    private Date lastImport;

    private BellBoardImport()
    {
        super();
    }

    public BellBoardImport(String url)
    {
        super();
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

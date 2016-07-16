package com.mills.performances.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getUrl().hashCode();
        result = 31 * result + (getLastImport() != null ? getLastImport().hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BellBoardImport rhs = (BellBoardImport) o;
        return new EqualsBuilder().append(getName(), rhs.getName())
                                  .append(getUrl(), rhs.getUrl())
                                  .append(getLastImport(), rhs.getLastImport())
                                  .build();

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", getName())
                                        .append("url", getUrl())
                                        .append("lastImport", getLastImport())
                                        .build();
    }
}

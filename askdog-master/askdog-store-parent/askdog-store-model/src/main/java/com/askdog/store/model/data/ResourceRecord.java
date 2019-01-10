package com.askdog.store.model.data;


import java.util.Date;

// TODO: OSS Agent
public class ResourceRecord extends Target {

    private Date creationDate;

    private String persistenceName;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPersistenceName() {
        return persistenceName;
    }

    public void setPersistenceName(String persistenceName) {
        this.persistenceName = persistenceName;
    }
}

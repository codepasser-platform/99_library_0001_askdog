package com.askdog.store.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class Owner extends Base {

    private static final long serialVersionUID = 6023253768804595715L;

    @Column(name = "creation_time", nullable = false, updatable = false)
    private Date creationTime;

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

}
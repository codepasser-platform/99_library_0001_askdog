package com.askdog.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ad_storage_link")
public class StorageLink extends Base {

    private static final long serialVersionUID = -3034207185289061666L;

    public enum Status {
        TEMPORARY, PERSISTENT, IN_USE
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}

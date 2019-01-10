package com.askdog.web.api.vo;

public class PresentationStatus {

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        ACCEPTED
    }

}

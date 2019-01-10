package com.askdog.service.exception;

public class IllegalArgumentException extends ServiceException {

    private static final long serialVersionUID = -6299563608663320053L;

    public enum Error {
        INVALID_TOKEN,
        INVALID_ORIGIN_PASSWORD
    }

    public IllegalArgumentException(Error error) {
        super(error);
    }

    @Override
    protected String reasonCode() {
        return "ARG";
    }

}

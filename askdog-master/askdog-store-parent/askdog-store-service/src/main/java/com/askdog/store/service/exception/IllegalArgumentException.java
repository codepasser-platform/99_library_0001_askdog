package com.askdog.store.service.exception;

public class IllegalArgumentException extends ServiceException {

    private static final long serialVersionUID = -394372113808762924L;

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

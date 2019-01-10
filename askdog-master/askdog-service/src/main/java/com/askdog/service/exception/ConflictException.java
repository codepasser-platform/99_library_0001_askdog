package com.askdog.service.exception;

public class ConflictException extends ServiceException {

    private static final long serialVersionUID = 3837761502401319670L;

    public enum Error {
        USER_NAME,
        USER_MAIL,
        ALREADY_ANSWERED
    }

    public ConflictException(Error error) {
        super(error);
    }

    @Override
    protected String reasonCode() {
        return "CONFLICT";
    }
}

package com.askdog.store.service.exception;

public class ConflictException extends ServiceException {

    private static final long serialVersionUID = 7845891947357756309L;

    public enum Error {
        USER_NAME,
    }

    public ConflictException(Error error) {
        super(error);
    }

    @Override
    protected String reasonCode() {
        return "CONFLICT";
    }
}

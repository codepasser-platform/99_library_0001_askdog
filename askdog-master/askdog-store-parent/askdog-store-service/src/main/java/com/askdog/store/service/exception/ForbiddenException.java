package com.askdog.store.service.exception;

public class ForbiddenException extends ServiceException {

    private static final long serialVersionUID = -3135032782563557690L;

    public enum Error {
        DELETE_ORDER,
        DEDUCTION_POINTS,
    }

    public ForbiddenException(Error error) {
        super(error);
    }

    @Override
    protected String reasonCode() {
        return "FORBIDDEN";
    }
}

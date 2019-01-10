package com.askdog.store.service.exception;

public class NotFoundException extends ServiceException {

    private static final long serialVersionUID = 8554706230569987583L;

    public enum Error {
        GOODS,
        DELIVERY,
        ORDER
    }

    public NotFoundException(Error error) {
        super(error);
    }

    @Override
    protected String reasonCode() {
        return "NOT_FOUND";
    }
}

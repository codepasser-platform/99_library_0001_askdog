package com.askdog.service.exception;

public class SearchingException extends ServiceException {

    private static final long serialVersionUID = 1804962617185235601L;

    public enum Error {
        SEARCH_EXCEPTION
    }

    public SearchingException(Enum<?> code) {
        this(code, null);
    }

    public SearchingException(Enum<?> code, Throwable cause) {
        super(code, cause);
    }

    @Override
    protected String reasonCode() {
        return "SEARCHING";
    }
}

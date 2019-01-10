package com.askdog.service.exception;

public class UtilException extends ServiceException {

    private static final long serialVersionUID = 1804962617185235601L;

    public enum Error {
        QUESTION_QR_GENERATE_FAILED
    }

    public UtilException(Enum<?> code) {
        this(code, null);
    }

    public UtilException(Enum<?> code, Throwable cause) {
        super(code, cause);
    }

    @Override
    protected String reasonCode() {
        return "UTIL";
    }
}

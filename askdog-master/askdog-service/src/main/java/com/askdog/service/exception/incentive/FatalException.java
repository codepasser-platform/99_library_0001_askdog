package com.askdog.service.exception.incentive;

import com.askdog.service.exception.ServiceException;

public class FatalException extends ServiceException {

    private static final long serialVersionUID = -1353879826567699228L;

    public enum Error {
        DEPENDENCY_DEAD_LOOP
    }

    public FatalException(Enum<?> code) {
        super(code);
    }

    @Override
    protected String reasonCode() {
        return "FATAL";
    }
}

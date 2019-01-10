package com.askdog.service.exception;

import com.askdog.common.exception.AbstractException;

public class ListenerException extends AbstractException {

    private static final long serialVersionUID = 3478659813899933231L;

    public enum Error {
        CYCLIC_DEPENDENCIES
    }

    public ListenerException(Enum<?> code) {
        super(code);
    }

    public ListenerException(Enum<?> code, Throwable cause) {
        super(code, cause);
    }

    public ListenerException(Error error) {
        super(error);
    }

    @Override
    protected String messageResourceBaseName() {
        return "exception.listener";
    }

    @Override
    protected String moduleName() {
        return "LISTENER";
    }
}

package com.askdog.store.service.exception;

import com.askdog.common.exception.AbstractException;

import static java.lang.String.format;

public abstract class ServiceException extends AbstractException {

    private static final long serialVersionUID = -7895124655645893467L;

    public ServiceException(Enum<?> code) {
        super(code);
    }

    public ServiceException(Enum<?> code, Throwable cause) {
        super(code, cause);
    }

    @Override
    protected final String messageResourceBaseName() {
        return "exception.service";
    }

    @Override
    protected final String moduleName() {
        return format("SRV_%s", reasonCode());
    }

    protected abstract String reasonCode();
}

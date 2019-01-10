package com.askdog.service.exception.incentive;

import com.askdog.service.exception.ServiceException;

public class ConfigureException extends ServiceException {

    private static final long serialVersionUID = -2146020040310401930L;

    public enum Error {
        JAVA_SCRIPT_RESOLVE_ERROR,
        GROOVY_RESOLVE_ERROR,
        UNSUPPORTED_EXPRESSION_TYPE
    }

    public ConfigureException(Enum<?> code) {
        super(code);
    }

    public ConfigureException(Enum<?> code, Throwable cause) {
        super(code, cause);
    }

    @Override
    protected String reasonCode() {
        return "CONF";
    }
}

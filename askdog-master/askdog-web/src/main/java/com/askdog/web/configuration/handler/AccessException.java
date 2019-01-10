package com.askdog.web.configuration.handler;

import com.askdog.common.exception.AbstractException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import static com.askdog.web.configuration.handler.AccessException.Error.AUTH_FAILED;
import static com.askdog.web.configuration.handler.AccessException.Error.DENIED;

public class AccessException extends AbstractException {

    private static final long serialVersionUID = 5147201406297373117L;

    public enum Error {
        DENIED, AUTH_FAILED
    }

    public AccessException() {
        super(DENIED);
    }

    public AccessException(AccessDeniedException exception) {
        super(DENIED, exception);
        setCodeValue(exception.getClass().getTypeName());
    }

    public AccessException(AuthenticationException exception) {
        super(AUTH_FAILED, exception);
        setCodeValue(exception.getClass().getTypeName());
    }

    @Override
    protected String messageResourceBaseName() {
        return "exception.web";
    }

    @Override
    protected String moduleName() {
        return "ACCESS";
    }
}

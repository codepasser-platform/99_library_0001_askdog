package com.askdog.store.web.api.exception;

import com.askdog.common.exception.AbstractException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

public class ReferenceException extends AbstractException {

    public enum Error {
        REFERENCE_FAILED
    }

    public ReferenceException(JpaObjectRetrievalFailureException e) {
        super(Error.REFERENCE_FAILED, e);
    }

    @Override
    protected String messageResourceBaseName() {
        return "exception.web";
    }

    @Override
    protected String moduleName() {
        return "WEB_VALIDATE";
    }
}

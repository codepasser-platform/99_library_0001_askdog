package com.askdog.web.api.exception;

import com.askdog.common.exception.AbstractException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Iterator;

import static com.askdog.web.api.exception.RequestValidateException.Error.FAILED;
import static com.google.common.base.Strings.isNullOrEmpty;

public class RequestValidateException extends AbstractException {

    private String detailMessage;

    public enum Error {
        FAILED
    }

    public RequestValidateException(MethodArgumentNotValidException exception) {
        setCode(FAILED);
        BindingResult bindingResult = exception.getBindingResult();
        FieldError firstFieldError = bindingResult.getFieldError();
        if (firstFieldError != null) {
            updateCodeMessage(firstFieldError);
        } else {
            Iterator<ObjectError> errors = bindingResult.getAllErrors().iterator();
            if (errors.hasNext()) {
                ObjectError objectError = errors.next();
                updateCodeMessage(objectError);
            }
        }
    }

    public RequestValidateException(IllegalArgumentException exception) {
        super(FAILED, exception);
    }

    @Override
    public String getMessage() {
        return detailMessage;
    }

    @Override
    protected String messageResourceBaseName() {
        return "exception.web";
    }

    @Override
    protected String moduleName() {
        return "WEB_VALIDATE";
    }

    private void updateCodeMessage(ObjectError objectError) {
        String[] codes = objectError.getCodes();
        if (codes != null && codes.length > 0) {
            setCodeValue(codes[0]);
            if (isNullOrEmpty(getMessage())) {
                this.detailMessage = objectError.getDefaultMessage();
            }
        }
    }
}
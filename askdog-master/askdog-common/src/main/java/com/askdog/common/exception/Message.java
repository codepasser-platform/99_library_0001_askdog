package com.askdog.common.exception;

public class Message {

    private String code;
    private String message;

    public Message(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Message(AbstractException exception) {
        this.code = exception.getCodeValue();
        this.message = getExceptionMessage(exception);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String getExceptionMessage(Throwable throwable) {
        Throwable cause = throwable.getCause();
        if (cause != null) {
            return getExceptionMessage(cause);
        }

        return throwable.getLocalizedMessage();
    }

}

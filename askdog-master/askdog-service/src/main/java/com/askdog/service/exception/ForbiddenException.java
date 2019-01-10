package com.askdog.service.exception;

public class ForbiddenException extends ServiceException {

    private static final long serialVersionUID = -3135032782563557690L;

    public enum Error {
        DELETE_QUESTION,
        UPDATE_QUESTION,
        DELETE_ANSWER,
        UPDATE_ANSWER,
        DELETE_QUESTION_COMMENT,
        UPDATE_QUESTION_COMMENT,
        DELETE_ANSWER_COMMENT,
        UPDATE_ANSWER_COMMENT,
        DEDUCTION_POINTS,
    }

    public ForbiddenException(Error error) {
        super(error);
    }

    @Override
    protected String reasonCode() {
        return "FORBIDDEN";
    }
}

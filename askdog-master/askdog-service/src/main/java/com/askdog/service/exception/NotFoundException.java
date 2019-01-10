package com.askdog.service.exception;

public class NotFoundException extends ServiceException {

    private static final long serialVersionUID = -2909833311527921881L;

    public enum Error {
        USER,
        QUESTION,
        ANSWER,
        QUESTION_COMMENT,
        ANSWER_COMMENT,
        NOTIFICATION,
        STORAGE_LINK,
        STORAGE_RECORD,
        QUESTION_LOCATION,
        USER_RESIDENCE,
        EVENT_LOG,
        INCENTIVE_LOG,
        QUESTION_SNAPSHOT,
        ANSWER_SNAPSHOT,
        COMMENT_SNAPSHOT
    }

    public NotFoundException(Error error) {
        super(error);
    }

    @Override
    protected String reasonCode() {
        return "NOT_FOUND";
    }
}

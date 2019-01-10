package com.askdog.service.event.core;

import com.askdog.model.entity.inner.EventType;

public class EventTypeGroupUtils {

    public enum EventTypeGroupType {
        QUESTION, ANSWER, QUESTION_COMMENT, ANSWER_COMMENT, OTHERS
    }

    public static EventTypeGroupType parseGroupType(EventType eventType) {
        switch (eventType) {
            case CREATE_QUESTION:
            case UP_VOTE_QUESTION:
            case DOWN_VOTE_QUESTION:
            case SHARE_QUESTION_NEARBY:
            case FOLLOW_QUESTION:
            case UN_FOLLOW_QUESTION:
            case VIEW_QUESTION:
            case SHARE_EXPERIENCE:
            case REPORT_QUESTION:
            case DELETE_QUESTION: {
                return EventTypeGroupType.QUESTION;
            }

            case CREATE_ANSWER:
            case UP_VOTE_ANSWER:
            case DOWN_VOTE_ANSWER:
            case FAV_ANSWER:
            case REPORT_ANSWER:
            case DELETE_ANSWER: {
                return EventTypeGroupType.ANSWER;
            }

            case REPORT_QUESTION_COMMENT:
            case CREATE_QUESTION_COMMENT: {
                return EventTypeGroupType.QUESTION_COMMENT;
            }

            case REPORT_ANSWER_COMMENT:
            case CREATE_ANSWER_COMMENT: {
                return EventTypeGroupType.ANSWER_COMMENT;
            }

            default:
                return EventTypeGroupType.OTHERS;
        }
    }

}

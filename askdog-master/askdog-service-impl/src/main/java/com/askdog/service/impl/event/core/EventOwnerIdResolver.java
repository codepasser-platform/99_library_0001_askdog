package com.askdog.service.impl.event.core;

import com.askdog.model.entity.inner.EventType;
import com.askdog.service.bo.CommentDetail;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.cell.AnswerCell;
import com.askdog.service.impl.cell.CommentCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static com.askdog.model.data.snapshot.CommentSnapshot.CommentType.ANSWER_COMMENT;
import static com.askdog.model.data.snapshot.CommentSnapshot.CommentType.QUESTION_COMMENT;
import static com.askdog.service.event.core.EventTypeGroupUtils.parseGroupType;

@Component
public class EventOwnerIdResolver {

    @Lazy @Autowired private AnswerCell answerCell;
    @Lazy @Autowired private CommentCell commentCell;

    public String resolve(EventType eventType, String targetId) throws ServiceException {
        switch (parseGroupType(eventType)) {
            case QUESTION_COMMENT:
                CommentDetail questionCommentDetail = commentCell.findDetailStateLessElseSnapshot(QUESTION_COMMENT, targetId);
                return questionCommentDetail.getOwnerId();

            case ANSWER:
                return answerCell.findDetailStateLessElseSnapshot(targetId).getQuestion().getUuid();

            case ANSWER_COMMENT:
                CommentDetail answerCommentDetail = commentCell.findDetailStateLessElseSnapshot(ANSWER_COMMENT, targetId);
                return answerCommentDetail.getOwnerId();

            default:
                return null;
        }
    }
}

package com.askdog.web.api.helper;

import com.askdog.model.data.EventLog;
import com.askdog.service.*;
import com.askdog.service.bo.AnswerDetail;
import com.askdog.service.bo.CommentDetail;
import com.askdog.service.bo.QuestionDetail;
import com.askdog.service.exception.ServiceException;
import com.askdog.web.api.vo.common.EventUser;
import com.askdog.web.api.vo.common.event.EventTarget;
import com.askdog.web.api.vo.common.event.EventType;
import com.askdog.web.api.vo.common.event.EventVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.askdog.service.event.core.EventTypeGroupUtils.parseGroupType;

@Component
public class EventHelper {

    @Autowired private UserService userService;
    @Autowired private QuestionService questionService;
    @Autowired private AnswerService answerService;
    @Autowired private QuestionCommentService questionCommentService;
    @Autowired private AnswerCommentService answerCommentService;
    @Autowired private EventLogService eventLogService;

    public EventVo convert(String eventLogId) throws ServiceException {

        EventLog eventLog = eventLogService.findById(eventLogId);
        String performerId = eventLog.getPerformerId();
        String targetId = eventLog.getTargetId();
        String ownerId = eventLog.getOwnerId();

        EventVo eventVo = new EventVo();
        eventVo.setType(new EventType().setCode(eventLog.getEventType().name()).setName(eventLog.getEventType().getDescription()));
        eventVo.setUser(new EventUser().setId(performerId).setUsername(userService.findDetail(performerId).getName()));

        switch (parseGroupType(eventLog.getEventType())) {
            case QUESTION: {
                QuestionDetail questionDetail = questionService.findDetailElseSnapshot("", targetId, true);
                eventVo.setTarget(new EventTarget().setId(targetId).setDescription(questionDetail.getSubject()).setDeleted(questionDetail.isDeleted()));
                break;
            }

            case ANSWER: {
                QuestionDetail questionDetail = questionService.findDetailElseSnapshot("", ownerId, true);
                AnswerDetail answerDetail = answerService.findDetailElseSnapshot("", targetId);
                eventVo.setOwner(new EventTarget().setId(ownerId).setDescription(questionDetail.getSubject()).setDeleted(questionDetail.isDeleted()));
                eventVo.setTarget(new EventTarget().setId(targetId).setDescription(answerDetail.getContent().textContent()).setDeleted(answerDetail.isDeleted()));
                break;
            }

            case QUESTION_COMMENT: {
                QuestionDetail questionDetail = questionService.findDetailElseSnapshot("", ownerId, true);
                CommentDetail commentDetail = questionCommentService.findDetailElseSnapshot("", targetId);
                eventVo.setOwner(new EventTarget().setId(ownerId).setDescription(questionDetail.getSubject()).setDeleted(questionDetail.isDeleted()));
                eventVo.setTarget(new EventTarget().setId(targetId).setDescription(commentDetail.getContent()).setDeleted(commentDetail.isDeleted()));
                break;
            }

            case ANSWER_COMMENT: {
                AnswerDetail answerDetail = answerService.findDetailElseSnapshot("", ownerId);
                CommentDetail commentDetail = answerCommentService.findDetailElseSnapshot("", targetId);
                eventVo.setOwner(new EventTarget().setId(ownerId).setDescription(answerDetail.getContent().textContent()).setDeleted(answerDetail.isDeleted()));
                eventVo.setTarget(new EventTarget().setId(targetId).setDescription(commentDetail.getContent()).setDeleted(commentDetail.isDeleted()));
                break;
            }
        }

        return eventVo;
    }
}

package com.askdog.web.api;

import com.askdog.model.entity.User;
import com.askdog.service.QuestionCommentService;
import com.askdog.service.bo.PureComment;
import com.askdog.service.bo.PureReport;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.event.annotation.TriggerEvent;
import com.askdog.service.event.annotation.TriggerEvent.TriggerEventItem;
import com.askdog.web.api.annotation.RestApiDelete;
import com.askdog.web.api.annotation.RestApiGet;
import com.askdog.web.api.annotation.RestApiPost;
import com.askdog.web.api.vo.PresentationComment;
import com.askdog.web.api.vo.PresentationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.askdog.model.entity.inner.EventType.CREATE_QUESTION_COMMENT;
import static com.askdog.model.entity.inner.EventType.REPORT_QUESTION_COMMENT;
import static com.askdog.web.api.vo.PresentationStatus.Status.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/questions/{questionId}/comments")
public class QuestionCommentApi {

    @Autowired
    private QuestionCommentService questionCommentService;

    @RequestMapping(method = RequestMethod.GET)
    public List<PresentationComment> comments(@PathVariable String questionId) throws ServiceException {
        return questionCommentService.findByQuestionId(questionId).stream().map(questionComment -> new PresentationComment().from(questionComment)).collect(Collectors.toList());
    }

    @RestApiGet
    public PresentationComment get(@PathVariable String id) throws ServiceException {
        return new PresentationComment().from(questionCommentService.find(id));
    }

    @RestApiPost
    @TriggerEvent(@TriggerEventItem(performerId = "user.uuid", eventType = CREATE_QUESTION_COMMENT, targetId = "returnValue.id"))
    public PresentationComment create(@Validated @RequestBody PureComment comment,
                                      @AuthenticationPrincipal User user,
                                      @PathVariable String questionId) throws ServiceException {
        return new PresentationComment().from(questionCommentService.create(user.getUuid(), questionId, comment));
    }

    @RestApiDelete
    public void delete(@AuthenticationPrincipal User user, @PathVariable String questionId, @PathVariable String id) throws ServiceException {
        questionCommentService.delete(user.getUuid(), questionId, id);
    }

    @RequestMapping(value = "/{id}/report", method = POST)
    @ResponseStatus(CREATED)
    @TriggerEvent(@TriggerEventItem(performerId = "user != null ? user.uuid : ''", eventType = REPORT_QUESTION_COMMENT, targetId = "commentId"))
    public PresentationStatus report(@AuthenticationPrincipal User user, @PathVariable("id") String commentId, @Valid @RequestBody PureReport pureReport) throws ServiceException {
        questionCommentService.report(user != null ? user.getUuid() : null, commentId, pureReport);
        PresentationStatus presentationStatus = new PresentationStatus();
        presentationStatus.setStatus(ACCEPTED);
        return presentationStatus;
    }
}

package com.askdog.web.api;

import com.askdog.model.data.inner.VoteDirection;
import com.askdog.model.entity.User;
import com.askdog.service.AnswerService;
import com.askdog.service.bo.AmendedAnswer;
import com.askdog.service.bo.PureReport;
import com.askdog.service.bo.VoteCount;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.event.annotation.TriggerEvent;
import com.askdog.service.event.annotation.TriggerEvent.TriggerEventItem;
import com.askdog.web.api.annotation.RestApiDelete;
import com.askdog.web.api.annotation.RestApiGet;
import com.askdog.web.api.annotation.RestApiPost;
import com.askdog.web.api.annotation.RestApiPut;
import com.askdog.web.api.vo.AnswerDetail;
import com.askdog.web.api.vo.PostedAnswer;
import com.askdog.web.api.vo.PresentationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.askdog.model.entity.inner.EventType.*;
import static com.askdog.model.entity.inner.EventType.UPDATE_ANSWER;
import static com.askdog.web.api.vo.PresentationStatus.Status.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/answers")
public class AnswerApi {

    @Autowired
    private AnswerService answerService;

    @RestApiGet
    public AnswerDetail get(@AuthenticationPrincipal User user, @PathVariable String id) throws ServiceException {
        return new AnswerDetail().from(answerService.findDetail(user != null ? user.getUuid() : null, id));
    }

    @RestApiPost
    @TriggerEvent(@TriggerEventItem(performerId = "user.uuid", eventType = CREATE_ANSWER, targetId = "returnValue.id"))
    public AnswerDetail create(@Validated @RequestBody PostedAnswer answer,
                               @AuthenticationPrincipal User user) throws ServiceException {
        return new AnswerDetail().from(answerService.create(user.getUuid(), answer.getQuestionId(), answer.convert()));
    }

    @RestApiPut
    @TriggerEvent(@TriggerEventItem(performerId = "user.uuid", eventType = UPDATE_ANSWER, targetId = "answerId"))
    public AnswerDetail update(@Validated @RequestBody AmendedAnswer amendedAnswer,
                                     @AuthenticationPrincipal User user,
                                     @PathVariable("id") String answerId) throws ServiceException {
        com.askdog.service.bo.AnswerDetail answerDetail = answerService.findDetail(user.getUuid(), answerId);
        answerService.update(user.getUuid(), answerDetail.getQuestion().getUuid(), answerId, amendedAnswer);
        return new AnswerDetail().from(answerService.findDetail(user.getUuid(), answerId));
    }

    @RestApiDelete
    @TriggerEvent(@TriggerEventItem(performerId = "user.uuid", eventType = DELETE_ANSWER, targetId = "id", prepare = "id"))
    public void delete(@AuthenticationPrincipal User user, @PathVariable String id) throws ServiceException {
        com.askdog.service.bo.AnswerDetail answerDetail = answerService.findDetail(user.getUuid(), id);
        answerService.delete(user.getUuid(), answerDetail.getQuestion().getUuid(), id);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/favorite", method = POST)
    @ResponseStatus(CREATED)
    @TriggerEvent(@TriggerEventItem(performerId = "user.uuid", eventType = FAV_ANSWER, targetId = "answerId"))
    public void favorite(@AuthenticationPrincipal User user, @PathVariable("id") String answerId) throws ServiceException {
        com.askdog.service.bo.AnswerDetail answerDetail = answerService.findDetail(user.getUuid(), answerId);
        answerService.favorite(user.getUuid(), answerDetail.getQuestion().getUuid(), answerId);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/favorite", method = DELETE)
    public void unfavorite(@AuthenticationPrincipal User user, @PathVariable("id") String answerId) throws ServiceException {
        com.askdog.service.bo.AnswerDetail answerDetail = answerService.findDetailElseSnapshot(user.getUuid(), answerId);
        answerService.unfavorite(user.getUuid(), answerDetail.getQuestion().getUuid(), answerId);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/vote", method = POST)
    @ResponseStatus(CREATED)
    @TriggerEvent({
            @TriggerEventItem(condition = "direction == T(com.askdog.model.data.inner.VoteDirection).UP", performerId = "user.uuid", eventType = UP_VOTE_ANSWER, targetId = "answerId"),
            @TriggerEventItem(condition = "direction == T(com.askdog.model.data.inner.VoteDirection).DOWN", performerId = "user.uuid", eventType = DOWN_VOTE_ANSWER, targetId = "answerId")
    })
    public VoteCount vote(@AuthenticationPrincipal User user,
                          @PathVariable("id") String answerId,
                          @RequestParam(value = "direction", defaultValue = "UP") VoteDirection direction) throws ServiceException {
        com.askdog.service.bo.AnswerDetail answerDetail = answerService.findDetail(user.getUuid(), answerId);
        return answerService.vote(user.getUuid(), answerDetail.getQuestion().getUuid(), answerId, direction);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/vote", method = DELETE)
    public VoteCount unvote(@AuthenticationPrincipal User user,
                            @PathVariable("id") String answerId) throws ServiceException {
        com.askdog.service.bo.AnswerDetail answerDetail = answerService.findDetail(user.getUuid(), answerId);
        return answerService.unvote(user.getUuid(), answerDetail.getQuestion().getUuid(), answerId);
    }

    @RequestMapping(value = "/{id}/report", method = POST)
    @ResponseStatus(CREATED)
    @TriggerEvent(@TriggerEventItem(performerId = "user != null ? user.uuid : ''", eventType = REPORT_ANSWER, targetId = "answerId"))
    public PresentationStatus report(@AuthenticationPrincipal User user, @PathVariable("id") String answerId, @Valid @RequestBody PureReport pureReport) throws ServiceException {
        answerService.report(user != null ? user.getUuid() : null, answerId, pureReport);
        PresentationStatus presentationStatus = new PresentationStatus();
        presentationStatus.setStatus(ACCEPTED);
        return presentationStatus;
    }

}

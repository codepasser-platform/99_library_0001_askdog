package com.askdog.web.api;

import com.askdog.model.data.ShareLog;
import com.askdog.model.data.inner.VoteDirection;
import com.askdog.model.entity.User;
import com.askdog.service.QuestionService;
import com.askdog.service.SearchService;
import com.askdog.service.bo.*;
import com.askdog.service.event.annotation.TriggerEvent;
import com.askdog.service.event.annotation.TriggerEvent.TriggerEventItem;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.search.SearchResult;
import com.askdog.web.api.annotation.RestApiDelete;
import com.askdog.web.api.annotation.RestApiGet;
import com.askdog.web.api.annotation.RestApiPost;
import com.askdog.web.api.annotation.RestApiPut;
import com.askdog.web.api.vo.PresentationAnswer;
import com.askdog.web.api.vo.PresentationStatus;
import com.askdog.web.api.vo.QuestionDetail;
import com.askdog.web.api.vo.QuestionDetailRelates;
import com.askdog.web.api.vo.common.PageResult;
import com.askdog.web.api.vo.search.QuestionRelatedTypeSearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.askdog.model.entity.inner.EventType.*;
import static com.askdog.web.api.utils.HeaderUtils.getRequestRealIp;
import static com.askdog.web.api.vo.PresentationStatus.Status.ACCEPTED;
import static com.askdog.web.api.vo.common.PageResultHelper.rePage;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionApi {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SearchService searchService;

    @RestApiGet
    @TriggerEvent(@TriggerEventItem(performerId = "user != null ? user.uuid : ''", eventType = VIEW_QUESTION, targetId = "id"))
    public QuestionDetailRelates get(@AuthenticationPrincipal User user, @PathVariable String id) throws ServiceException {

        // TODO should be call through a separate RESTful API (the frontend has the logic to check the view action)
        questionService.view(user != null ? user.getUuid() : null, id);
        QuestionDetailRelates questionDetail = new QuestionDetailRelates().from(questionService.findDetail(user != null ? user.getUuid() : null, id));

        QuestionRelatedTypeSearchParam questionRelatedTypeSearchParam = new QuestionRelatedTypeSearchParam();
        questionRelatedTypeSearchParam.setType("question_related");
        questionRelatedTypeSearchParam.setQuestionId(id);
        questionRelatedTypeSearchParam.setFrom(0);
        questionRelatedTypeSearchParam.setSize(20);
        questionRelatedTypeSearchParam.setTailored(false);

        SearchResult searchResult = searchService.related(id, questionRelatedTypeSearchParam.convert());
        questionDetail.setRelates(searchResult.getResult());

        return questionDetail;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", method = GET, params = {"related=false"})
    public QuestionDetail getWithoutRelated(@AuthenticationPrincipal User user, @PathVariable String id) throws ServiceException {
        // TODO should be call through a separate RESTful API (the frontend has the logic to check the view action)
        questionService.view(user != null ? user.getUuid() : null, id);
        return new QuestionDetailRelates().from(questionService.findDetail(user != null ? user.getUuid() : null, id));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", method = GET, params = {"edit=true"})
    public ResponseEntity<EditQuestion> getEdit(@AuthenticationPrincipal User user,
                                                @PathVariable String id) throws ServiceException {
        com.askdog.service.bo.QuestionDetail questionDetail = questionService.findDetail(user.getUuid(), id);

        if (!questionDetail.isMine()) {
            return ResponseEntity.status(FORBIDDEN).body(null);
        }

        return ResponseEntity.status(OK).body(new EditQuestion().from(questionDetail));
    }

    @RestApiPut
    @TriggerEvent(@TriggerEventItem(performerId = "user.uuid", eventType = UPDATE_QUESTION, targetId = "id"))
    public QuestionDetail update(@Validated @RequestBody AmendedQuestion amendedQuestion,
                                 @AuthenticationPrincipal User user,
                                 @PathVariable String id) throws ServiceException {
        questionService.update(user.getUuid(), id, amendedQuestion);
        return new QuestionDetail().from(questionService.findDetail(user.getUuid(), id));
    }

    @RestApiPost
    @TriggerEvent({
            @TriggerEventItem(condition = "pureQuestion.shareAnswer == null", performerId = "user.uuid", eventType = CREATE_QUESTION, targetId = "returnValue.id"),
            @TriggerEventItem(condition = "pureQuestion.shareAnswer != null", performerId = "user.uuid", eventType = SHARE_EXPERIENCE, targetId = "returnValue.id")
    })
    public QuestionDetail create(@Validated @RequestBody PureQuestion pureQuestion,
                                 @AuthenticationPrincipal User user, HttpServletRequest request) throws ServiceException {
        pureQuestion.setIp(getRequestRealIp(request));
        String questionId = questionService.create(user.getUuid(), pureQuestion);
        return new QuestionDetail().from(questionService.findDetail(user.getUuid(), questionId));
    }

    @RestApiDelete
    @TriggerEvent(@TriggerEventItem(performerId = "user.uuid", eventType = DELETE_QUESTION, targetId = "id"))
    public void delete(@AuthenticationPrincipal User user, @PathVariable String id) throws ServiceException {
        questionService.delete(user.getUuid(), id);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/follow", method = POST)
    @ResponseStatus(CREATED)
    public void follow(@AuthenticationPrincipal User user, @PathVariable("id") String questionId) throws ServiceException {
        questionService.follow(user.getUuid(), questionId);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/follow", method = DELETE)
    public void unfollow(@AuthenticationPrincipal User user, @PathVariable("id") String questionId) throws ServiceException {
        questionService.unfollow(user.getUuid(), questionId);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/share", method = POST)
    @TriggerEvent(@TriggerEventItem(performerId = "user.uuid", eventType = SHARE_QUESTION_NEARBY, targetId = "questionId"))
    public ShareLog share(@AuthenticationPrincipal User user, @PathVariable("id") String questionId, @RequestParam("lat") Double lat, @RequestParam("lng") Double lng) throws ServiceException {
        return questionService.share(user.getUuid(), questionId, lat, lng);
    }

    @RequestMapping(value = "/{id}/share_question", method = POST)
    @TriggerEvent(@TriggerEventItem(performerId = "user != null ? user.uuid : ''", eventType = SHARE_QUESTION, targetId = "questionId"))
    public void shareQuestion(@AuthenticationPrincipal User user, @PathVariable("id") String questionId) throws ServiceException {
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/vote", method = POST)
    @ResponseStatus(CREATED)
    @TriggerEvent({
            @TriggerEventItem(condition = "direction == T(com.askdog.model.data.inner.VoteDirection).UP", performerId = "user.uuid", eventType = UP_VOTE_QUESTION, targetId = "questionId"),
            @TriggerEventItem(condition = "direction == T(com.askdog.model.data.inner.VoteDirection).DOWN", performerId = "user.uuid", eventType = DOWN_VOTE_QUESTION, targetId = "questionId")
    })
    public VoteCount vote(@AuthenticationPrincipal User user,
                          @PathVariable("id") String questionId,
                          @RequestParam(value = "direction", defaultValue = "UP") VoteDirection direction) throws ServiceException {
        return questionService.vote(user.getUuid(), questionId, direction);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/vote", method = DELETE)
    public VoteCount unvote(@AuthenticationPrincipal User user,
                            @PathVariable("id") String questionId) throws ServiceException {
        return questionService.unvote(user.getUuid(), questionId);
    }

    @RequestMapping(value = "/{id}/report", method = POST)
    @ResponseStatus(CREATED)
    @TriggerEvent(@TriggerEventItem(performerId = "user != null ? user.uuid : ''", eventType = REPORT_QUESTION, targetId = "questionId"))
    public PresentationStatus report(@AuthenticationPrincipal User user, @PathVariable("id") String questionId, @Valid @RequestBody PureReport pureReport) throws ServiceException {
        questionService.report(user != null ? user.getUuid() : null, questionId, pureReport);
        PresentationStatus presentationStatus = new PresentationStatus();
        presentationStatus.setStatus(ACCEPTED);
        return presentationStatus;
    }

    @RequestMapping(value = "/{questionId}/answers", method = GET)
    public PageResult<PresentationAnswer> answers(@PathVariable("questionId") String questionId, Pageable pageable) throws ServiceException {
        return rePage(questionService.findAnswers(questionId, pageable), answerDetail -> new PresentationAnswer().from(answerDetail));
    }
}

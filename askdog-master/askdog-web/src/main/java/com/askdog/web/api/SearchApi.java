package com.askdog.web.api;

import com.askdog.model.entity.User;
import com.askdog.service.SearchService;
import com.askdog.service.UserService;
import com.askdog.service.exception.SearchingException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.search.SearchRequest;
import com.askdog.service.search.SearchResult;
import com.askdog.web.api.vo.search.FullTextTypeSearchParam;
import com.askdog.web.api.vo.search.QuestionRelatedTypeSearchParam;
import com.askdog.web.api.vo.search.SimilarTypeSearchParam;
import com.askdog.web.api.vo.search.TypeSearchParam;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/search")
public class SearchApi {

    @Autowired private UserService userService;
    @Autowired private SearchService searchService;

    @RequestMapping(method = RequestMethod.GET, params = {"type=similar"})
    public SearchResult similar(@Valid SimilarTypeSearchParam similarSearch) throws SearchingException {
        return searchService.similar(similarSearch.convert());
    }

    @RequestMapping(method = RequestMethod.GET, params = {"type=search"})
    public SearchResult search(@Valid FullTextTypeSearchParam fullTextSearch) throws SearchingException {
        return searchService.search(fullTextSearch.convert());
    }

    @RequestMapping(method = RequestMethod.GET, params = {"type=question_new"})
    public SearchResult questionNew(@AuthenticationPrincipal User user, @Valid TypeSearchParam typeSearchParam) throws ServiceException {
        return searchService.questionNew(user, initTailoredFlag(user, typeSearchParam));
    }

    @RequestMapping(method = RequestMethod.GET, params = {"type=question_hot"})
    public SearchResult questionHot(@AuthenticationPrincipal User user, @Valid TypeSearchParam typeSearchParam) throws ServiceException {
        return searchService.questionHot(user, initTailoredFlag(user, typeSearchParam));
    }

    @RequestMapping(method = RequestMethod.GET, params = {"type=answer_new"})
    public SearchResult answerNew(@AuthenticationPrincipal User user, @Valid TypeSearchParam typeSearchParam) throws ServiceException {
        return searchService.answerNew(user, initTailoredFlag(user, typeSearchParam));
    }

    @RequestMapping(method = RequestMethod.GET, params = {"type=trends"})
    public SearchResult trends(@AuthenticationPrincipal User user, @Valid TypeSearchParam typeSearchParam) throws ServiceException {
        return searchService.trends(user, initTailoredFlag(user, typeSearchParam));
    }

    @RequestMapping(method = RequestMethod.GET, params = {"type=waiting_answer"})
    public SearchResult waitingAnswer(@AuthenticationPrincipal User user, @Valid TypeSearchParam typeSearchParam) throws ServiceException {
        return searchService.waitingAnswer(user, initTailoredFlag(user, typeSearchParam));
    }

    @RequestMapping(method = RequestMethod.GET, params = {"type=similar_question"})
    public SearchResult similarQuestion(@Valid SimilarTypeSearchParam similarSearch) throws SearchingException {
        return similar(similarSearch);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"type=question_related"})
    public SearchResult related(@Valid QuestionRelatedTypeSearchParam questionRelatedTypeSearchParam) throws ServiceException {
        return searchService.related(questionRelatedTypeSearchParam.getQuestionId(), questionRelatedTypeSearchParam.convert());
    }

    @SuppressWarnings("unchecked")
    private SearchRequest initTailoredFlag(User user, TypeSearchParam typeSearchParam) throws ServiceException {
        SearchRequest searchRequest = typeSearchParam.convert();

        if (user != null && typeSearchParam.isTailored()) {
            searchRequest.getParams().put("user_labels", behaviorLabels(user.getUuid()));
        }

        return searchRequest;
    }

    private String behaviorLabels(@Nonnull String userId) {
        return Joiner.on(",").join(userService.behaviorLabels(userId));
    }

}

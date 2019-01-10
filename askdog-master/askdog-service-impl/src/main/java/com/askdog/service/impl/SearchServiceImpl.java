package com.askdog.service.impl;

import com.askdog.model.entity.Label;
import com.askdog.model.entity.Question;
import com.askdog.model.entity.User;
import com.askdog.service.QuestionService;
import com.askdog.service.SearchService;
import com.askdog.service.bo.AnswerDetail;
import com.askdog.service.bo.QuestionDetail;
import com.askdog.service.exception.SearchingException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.cell.AnswerCell;
import com.askdog.service.impl.cell.QuestionCell;
import com.askdog.service.impl.cell.UserCell;
import com.askdog.service.search.SearchAgent;
import com.askdog.service.search.SearchRequest;
import com.askdog.service.search.SearchResult;
import com.askdog.service.utils.MapUtils;
import com.askdog.service.utils.ProcessPipline.ProcessPipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired private UserCell userCell;
    @Autowired private AnswerCell answerCell;
    @Autowired private SearchAgent searchAgent;
    @Autowired private QuestionCell questionCell;
    @Autowired private QuestionService questionService;

    @Override public SearchResult similar(@Valid SearchRequest searchRequest) throws SearchingException {
        return searchAgent.search(searchRequest);
    }

    @Override public SearchResult search(@Valid SearchRequest searchRequest) throws SearchingException {
        return searchAgent.search(searchRequest);
    }

    @Override public SearchResult questionNew(User user, @Valid SearchRequest searchRequest) throws ServiceException {
        return searchWithActionStatus(user, searchRequest);
    }

    @Override public SearchResult questionHot(User user, @Valid SearchRequest searchRequest) throws ServiceException {
        return searchWithActionStatus(user, searchRequest);
    }

    @Override public SearchResult answerNew(User user, @Valid SearchRequest searchRequest) throws ServiceException {
        return searchWithActionStatus(user, searchRequest);
    }

    @Override public SearchResult trends(User user, @Valid SearchRequest searchRequest) throws ServiceException {
        return searchWithActionStatus(user, searchRequest);
    }

    @Override public SearchResult waitingAnswer(User user, @Valid SearchRequest searchRequest) throws ServiceException {
        return searchWithActionStatus(user, searchRequest);
    }

    @Override
    public SearchResult related(String questionId, @Valid SearchRequest searchRequest) throws ServiceException {
        return searchAgent.search(setRelateParam(questionId, searchRequest));
    }

    public SearchRequest setRelateParam(String questionId, SearchRequest searchRequest) throws ServiceException {
        Question question = questionService.find(questionId);

        if (question.getLabels() != null) {
            searchRequest.getParams().put("question_labels", question.getLabels().stream().<String>map(Label::getName).reduce((s, s2) -> s + " " + s2).orElse(""));
        }

        searchRequest.getParams().put("subject", question.getSubject());

        return searchRequest;
    }

    @SuppressWarnings("unchecked")
    private SearchResult searchWithActionStatus(User user, SearchRequest searchRequest) throws ServiceException {
        SearchResult searchResult = searchAgent.search(searchRequest);
        List<Map<String, Object>> result = searchResult.getResult();

        if (result != null) {
            for (Map<String, Object> questionMap : result) {

                String questionId = (String) questionMap.get("id");

                QuestionDetail questionDetail = user == null
                        ? questionCell.findDetailStateLessElseSnapshot(questionId)
                        : questionCell.findDetailElseSnapshot(user.getUuid(), questionId, false);

                questionMap.put("mine", questionDetail.isMine());
                questionMap.put("qr_link", questionDetail.getQrLink());
                questionMap.put("followed", questionDetail.isFollowed());
                questionMap.put("follow_count", questionDetail.getFollowCount());
                questionMap.put("views", questionDetail.getViewCount());

                ProcessPipline.init(questionMap)
                        .process((question) -> (Map<String, Object>) question.get("best_answer"))
                        .filter(bestAnswer -> bestAnswer != null)
                        .process((bestAnswer) -> initAnswerVoteInfo(bestAnswer, user));

                ProcessPipline.init(questionMap)
                        .process((question) -> (Map<String, Object>) question.get("newest_answer"))
                        .filter(newestAnswer -> newestAnswer != null)
                        .process((newestAnswer) -> initAnswerVoteInfo(newestAnswer, user));

                ProcessPipline.init(questionMap)
                        .process(question -> (String) MapUtils.getPathValue(question, "author.id"))
                        .filter(authorId -> authorId != null)
                        .process(authorId -> userCell.findDetail(authorId))
                        .process(author -> MapUtils.setPathValue(questionMap, "author.avatar", author.getAvatar()));

                ProcessPipline.init(questionMap)
                        .process(question -> (String) MapUtils.getPathValue(question, "best_answer.answerer.id"))
                        .filter(answererId -> answererId != null)
                        .process(answererId -> userCell.findDetail(answererId))
                        .process(answerer -> MapUtils.setPathValue(questionMap, "best_answer.answerer.avatar", answerer.getAvatar()));

                ProcessPipline.init(questionMap)
                        .process(question -> (String) MapUtils.getPathValue(question, "newest_answer.answerer.id"))
                        .filter(answererId -> answererId != null)
                        .process(answererId -> userCell.findDetail(answererId))
                        .process(answerer -> MapUtils.setPathValue(questionMap, "newest_answer.answerer.avatar", answerer.getAvatar()));
            }
        }

        return searchResult;
    }

    private Map<String, Object> initAnswerVoteInfo(Map<String, Object> answer, User user) throws ServiceException {
        String answerId = (String) answer.get("id");

        AnswerDetail answerDetail = user == null
                ? answerCell.findDetailStateLessElseSnapshot(answerId)
                : answerCell.findDetailElseSnapshot(user.getUuid(), answerId, false);

        answer.put("up_voted", answerDetail.isUpVoted());
        answer.put("up_vote_count", answerDetail.getUpVoteCount());
        return answer;
    }
}

package com.askdog.service;

import com.askdog.model.data.ShareLog;
import com.askdog.model.data.inner.VoteDirection;
import com.askdog.model.entity.Question;
import com.askdog.service.bo.*;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.feature.Reportable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface QuestionService extends Reportable {

    @Nonnull Question find(@Nonnull String id) throws ServiceException;

    @Nonnull QuestionDetail findDetailElseSnapshot(@Nonnull String userId, @Nonnull String questionId, boolean fetchAnswerDetail) throws ServiceException;

    @Nonnull String create(@Nonnull String userId, @Nonnull PureQuestion pureQuestion) throws ServiceException;

    void delete(@Nonnull String userId, @Nonnull String questionId) throws ServiceException;

    void update(@Nonnull String userId, @Nonnull String questionId, @Nonnull AmendedQuestion amendedQuestion) throws ServiceException;

    @Nonnull QuestionDetail findDetail(String userId, @Nonnull String questionId) throws ServiceException;

    @Nonnull Optional<AnswerDetail> findLatestAnswer(@Nonnull String questionId);

    @Nonnull QuestionInteraction interaction(@Nonnull String questionId);

    void view(String userId, @Nonnull String questionId) throws ServiceException;

    void follow(@Nonnull String userId, @Nonnull String questionId) throws ServiceException;

    void unfollow(@Nonnull String userId, @Nonnull String questionId) throws ServiceException;

    ShareLog share(@Nonnull String userId, @Nonnull String questionId, @Nonnull Double lat, @Nonnull Double lng) throws ServiceException;

    @Nonnull
    Page<AnswerDetail> findAnswers(@Nonnull String questionId, @Nonnull Pageable paging) throws ServiceException;

    VoteCount vote(@Nonnull String userId, @Nonnull String questionId, @Nonnull VoteDirection direction) throws ServiceException;

    VoteCount unvote(@Nonnull String userId, @Nonnull String questionId) throws ServiceException;
}

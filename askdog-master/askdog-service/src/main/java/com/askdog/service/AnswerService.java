package com.askdog.service;

import com.askdog.model.data.inner.VoteDirection;
import com.askdog.model.entity.Answer;
import com.askdog.service.bo.AmendedAnswer;
import com.askdog.service.bo.AnswerDetail;
import com.askdog.service.bo.PureAnswer;
import com.askdog.service.bo.VoteCount;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.feature.Reportable;

import javax.annotation.Nonnull;

public interface AnswerService extends Reportable {

    @Nonnull
    Answer find(@Nonnull String answerId) throws ServiceException;

    @Nonnull
    AnswerDetail findDetail(String userId, @Nonnull String answerId) throws ServiceException;

    @Nonnull
    AnswerDetail findDetailElseSnapshot(String userId, @Nonnull String answerId) throws ServiceException;

    @Nonnull
    AnswerDetail create(@Nonnull String userId, @Nonnull String questionId, @Nonnull PureAnswer answer) throws ServiceException;

    void delete(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId) throws ServiceException;

    void update(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull AmendedAnswer amendedAnswer) throws ServiceException;

    void favorite(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId) throws ServiceException;

    void unfavorite(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId) throws ServiceException;

    VoteCount vote(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull VoteDirection direction) throws ServiceException;

    VoteCount unvote(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId) throws ServiceException;

}

package com.askdog.service.proxy;

import com.askdog.model.data.inner.VoteDirection;
import com.askdog.model.entity.Answer;
import com.askdog.service.AnswerService;
import com.askdog.service.bo.*;
import com.askdog.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Nonnull
    @Override
    public Answer find(@Nonnull String answerId) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public AnswerDetail findDetail(String userId, @Nonnull String answerId) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public AnswerDetail findDetailElseSnapshot(String userId, @Nonnull String answerId) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public AnswerDetail create(@Nonnull String userId, @Nonnull String questionId, @Nonnull PureAnswer answer) throws ServiceException {
        return null;
    }

    @Override
    public void delete(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId) throws ServiceException {

    }

    @Override
    public void update(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull AmendedAnswer amendedAnswer) throws ServiceException {

    }

    @Override
    public void favorite(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId) throws ServiceException {

    }

    @Override
    public void unfavorite(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId) throws ServiceException {

    }

    @Override
    public VoteCount vote(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull VoteDirection direction) throws ServiceException {
        return null;
    }

    @Override
    public VoteCount unvote(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId) throws ServiceException {
        return null;
    }

    @Override
    public void report(@Nullable String userId, @Nonnull String target, @Nonnull PureReport pureReport) throws ServiceException {

    }
}
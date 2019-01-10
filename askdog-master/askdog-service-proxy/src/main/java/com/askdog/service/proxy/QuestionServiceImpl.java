package com.askdog.service.proxy;

import com.askdog.model.data.ShareLog;
import com.askdog.model.data.inner.VoteDirection;
import com.askdog.model.entity.Question;
import com.askdog.service.QuestionService;
import com.askdog.service.bo.*;
import com.askdog.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class QuestionServiceImpl implements QuestionService {

    @Nonnull
    @Override
    public Question find(@Nonnull String id) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public QuestionDetail findDetailElseSnapshot(@Nonnull String userId, @Nonnull String questionId, boolean fetchAnswerDetail) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public String create(@Nonnull String userId, @Nonnull PureQuestion pureQuestion) throws ServiceException {
        return null;
    }

    @Override
    public void delete(@Nonnull String userId, @Nonnull String questionId) throws ServiceException {

    }

    @Override
    public void update(@Nonnull String userId, @Nonnull String questionId, @Nonnull AmendedQuestion amendedQuestion) throws ServiceException {

    }

    @Nonnull
    @Override
    public QuestionDetail findDetail(String userId, @Nonnull String questionId) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public Optional<AnswerDetail> findLatestAnswer(@Nonnull String questionId) {
        return null;
    }

    @Nonnull
    @Override
    public QuestionInteraction interaction(@Nonnull String questionId) {
        return null;
    }

    @Override
    public void view(String userId, @Nonnull String questionId) throws ServiceException {

    }

    @Override
    public void follow(@Nonnull String userId, @Nonnull String questionId) throws ServiceException {

    }

    @Override
    public void unfollow(@Nonnull String userId, @Nonnull String questionId) throws ServiceException {

    }

    @Override
    public ShareLog share(@Nonnull String userId, @Nonnull String questionId, @Nonnull Double lat, @Nonnull Double lng) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public Page<AnswerDetail> findAnswers(@Nonnull String questionId, @Nonnull Pageable paging) throws ServiceException {
        return null;
    }

    @Override
    public VoteCount vote(@Nonnull String userId, @Nonnull String questionId, @Nonnull VoteDirection direction) throws ServiceException {
        return null;
    }

    @Override
    public VoteCount unvote(@Nonnull String userId, @Nonnull String questionId) throws ServiceException {
        return null;
    }

    @Override
    public void report(@Nullable String userId, @Nonnull String target, @Nonnull PureReport pureReport) throws ServiceException {

    }
}

package com.askdog.service.proxy;

import com.askdog.model.entity.AnswerComment;
import com.askdog.service.AnswerCommentService;
import com.askdog.service.bo.CommentDetail;
import com.askdog.service.bo.PureComment;
import com.askdog.service.bo.PureReport;
import com.askdog.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Service
public class AnswerCommentServiceImpl implements AnswerCommentService {

    @Nonnull
    @Override
    public CommentDetail find(@Nonnull String id) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public CommentDetail findDetailElseSnapshot(@Nonnull String userId, @Nonnull String commentId) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public CommentDetail create(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull PureComment pureComment) throws ServiceException {
        return null;
    }

    @Override
    public void delete(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull String answerCommentId) throws ServiceException {

    }

    @Nonnull
    @Override
    public AnswerComment update(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull String answerCommentId, @Nonnull String comment) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public List<CommentDetail> findByAnswerId(@Nonnull String answerId) throws ServiceException {
        return null;
    }

    @Override
    public void report(@Nullable String userId, @Nonnull String target, @Nonnull PureReport pureReport) throws ServiceException {

    }
}

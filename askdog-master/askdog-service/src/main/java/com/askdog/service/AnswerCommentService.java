package com.askdog.service;

import com.askdog.model.entity.AnswerComment;
import com.askdog.service.bo.CommentDetail;
import com.askdog.service.bo.PureComment;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.feature.Reportable;

import javax.annotation.Nonnull;
import java.util.List;

public interface AnswerCommentService extends Reportable {

    @Nonnull CommentDetail find(@Nonnull String id) throws ServiceException;

    @Nonnull CommentDetail findDetailElseSnapshot(@Nonnull String userId, @Nonnull String commentId) throws ServiceException;

    @Nonnull CommentDetail create(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull PureComment pureComment) throws ServiceException;

    void delete(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull String answerCommentId) throws ServiceException;

    @Nonnull AnswerComment update(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull String answerCommentId, @Nonnull String comment) throws ServiceException;

    @Nonnull List<CommentDetail> findByAnswerId(@Nonnull String answerId) throws ServiceException;

}

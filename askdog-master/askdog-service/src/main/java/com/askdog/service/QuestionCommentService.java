package com.askdog.service;

import com.askdog.model.entity.QuestionComment;
import com.askdog.service.bo.CommentDetail;
import com.askdog.service.bo.PureComment;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.feature.Reportable;

import javax.annotation.Nonnull;
import java.util.List;

public interface QuestionCommentService extends Reportable {

    @Nonnull CommentDetail find(@Nonnull String id) throws ServiceException;

    @Nonnull CommentDetail findDetailElseSnapshot(@Nonnull String userId, @Nonnull String commentId) throws ServiceException;

    @Nonnull CommentDetail create(@Nonnull String userId, @Nonnull String questionId, @Nonnull PureComment pureComment) throws ServiceException;

    void delete(@Nonnull String userId, @Nonnull String questionId, @Nonnull String questionCommentId) throws ServiceException;

    @Nonnull QuestionComment update(@Nonnull String userId, @Nonnull String questionId, @Nonnull String questionCommentId, @Nonnull String comment) throws ServiceException;

    @Nonnull List<CommentDetail> findByQuestionId(@Nonnull String questionId) throws ServiceException;

}

package com.askdog.service.feature;

import com.askdog.model.data.inner.VoteDirection;
import com.askdog.service.bo.VoteCount;
import com.askdog.service.exception.ServiceException;

import javax.annotation.Nonnull;

public interface Voteable {
    VoteCount vote(@Nonnull String userId, @Nonnull String target, @Nonnull VoteDirection direction) throws ServiceException;
    VoteCount unvote(@Nonnull String userId, @Nonnull String target) throws ServiceException;
}

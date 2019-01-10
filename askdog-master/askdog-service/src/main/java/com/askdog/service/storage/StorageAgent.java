package com.askdog.service.storage;

import com.askdog.model.data.StorageRecord;
import com.askdog.model.data.inner.ResourceType;
import com.askdog.service.exception.ServiceException;

import javax.annotation.Nonnull;
import java.io.InputStream;

public interface StorageAgent {

    @Nonnull
    AccessToken generateAccessToken(@Nonnull ResourceType type, @Nonnull String fileName) throws ServiceException;

    @Nonnull
    StorageResource persistLink(@Nonnull String linkId) throws ServiceException;

    @Nonnull
    StorageResource find(@Nonnull String linkId) throws ServiceException;

    @Nonnull
    StorageRecord save(@Nonnull ResourceType type, @Nonnull String fileName, @Nonnull InputStream stream) throws ServiceException;

    @Nonnull
    String resourceURL(@Nonnull ResourceType type, @Nonnull String fileName) throws ServiceException;
}

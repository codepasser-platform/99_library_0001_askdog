package com.askdog.service;

import com.askdog.model.data.inner.ResourceType;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.storage.AccessToken;
import com.askdog.service.storage.StorageResource;

import javax.annotation.Nonnull;

public interface StorageService {

    @Nonnull
    AccessToken generateAccessToken(@Nonnull ResourceType type, @Nonnull String fileName) throws ServiceException;

    @Nonnull
    StorageResource persistLink(@Nonnull String linkId) throws ServiceException;

}

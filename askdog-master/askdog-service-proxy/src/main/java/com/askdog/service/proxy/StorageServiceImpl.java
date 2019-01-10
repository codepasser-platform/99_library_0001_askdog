package com.askdog.service.proxy;

import com.askdog.model.data.inner.ResourceType;
import com.askdog.service.StorageService;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.storage.AccessToken;
import com.askdog.service.storage.StorageResource;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
public class StorageServiceImpl implements StorageService {

    @Nonnull
    @Override
    public AccessToken generateAccessToken(@Nonnull ResourceType type, @Nonnull String fileName) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public StorageResource persistLink(@Nonnull String linkId) throws ServiceException {
        return null;
    }
}

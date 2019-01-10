package com.askdog.service.impl;

import com.askdog.model.data.inner.ResourceType;
import com.askdog.service.StorageService;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.storage.AccessToken;
import com.askdog.service.storage.StorageResource;
import com.askdog.service.storage.Provider;
import com.askdog.service.storage.StorageAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

import static com.askdog.model.data.inner.StorageProvider.ALI_OSS;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    @Provider(ALI_OSS)
    private StorageAgent storageAgent;

    @Nonnull
    @Override
    public AccessToken generateAccessToken(@Nonnull ResourceType type, @Nonnull String fileName) throws ServiceException {
        return storageAgent.generateAccessToken(type, fileName);
    }

    @Nonnull
    @Override
    public StorageResource persistLink(@Nonnull String linkId) throws ServiceException {
        return storageAgent.persistLink(linkId);
    }
}

package com.askdog.service.impl;

import com.askdog.model.entity.ExternalUser;
import com.askdog.model.repository.ExternalUserRepository;
import com.askdog.service.ExternalUserService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.util.Optional;

@Service
public class ExternalUserServiceImpl implements ExternalUserService {

    @Autowired
    private ExternalUserRepository externalUserRepository;

    @Nonnull
    @Override
    public ExternalUser save(@Valid ExternalUser externalUser) {
        return externalUserRepository.save(externalUser);
    }

    @Nonnull
    @Override
    public Optional<ExternalUser> findByExternalUserId(@Nonnull String externalUserId) {
        Preconditions.checkArgument(!StringUtils.isEmpty(externalUserId));
        return externalUserRepository.findByExternalUserId(externalUserId);
    }
}

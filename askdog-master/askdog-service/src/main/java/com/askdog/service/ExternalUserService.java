package com.askdog.service;

import com.askdog.model.entity.ExternalUser;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.util.Optional;

public interface ExternalUserService {
    @Nonnull ExternalUser save(@Valid ExternalUser externalUser);
    @Nonnull Optional<ExternalUser> findByExternalUserId(@Nonnull String externalUserId);
}

package com.askdog.service.proxy;

import com.askdog.model.entity.ExternalUser;
import com.askdog.service.ExternalUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.util.Optional;

@Service
public class ExternalUserServiceImpl implements ExternalUserService {

    @Nonnull
    @Override
    public ExternalUser save(@Valid ExternalUser externalUser) {
        return null;
    }

    @Nonnull
    @Override
    public Optional<ExternalUser> findByExternalUserId(@Nonnull String externalUserId) {
        return null;
    }
}

package com.askdog.service;

import javax.annotation.Nonnull;

public interface TokenService {
    boolean isTokenValidate(@Nonnull String tokenName, @Nonnull String token);
}

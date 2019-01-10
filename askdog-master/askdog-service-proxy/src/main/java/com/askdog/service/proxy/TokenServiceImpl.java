package com.askdog.service.proxy;

import com.askdog.service.TokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public boolean isTokenValidate(@Nonnull String tokenName, @Nonnull String token) {
        return false;
    }
}

package com.askdog.service.impl;

import com.askdog.model.repository.TokenRepository;
import com.askdog.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public boolean isTokenValidate(@Nonnull String tokenName, @Nonnull String token) {
        return tokenRepository.isTokenValidate(tokenName, token);
    }

}

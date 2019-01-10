package com.askdog.web.configuration.oauth2.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class TextOAuth2AccessTokenMessageConverter extends AbstractJackson2HttpMessageConverter {

    public TextOAuth2AccessTokenMessageConverter() {
        this(Jackson2ObjectMapperBuilder.json().build());
    }

    @SuppressWarnings("WeakerAccess")
    public TextOAuth2AccessTokenMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.TEXT_PLAIN);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return OAuth2AccessToken.class.equals(clazz);
    }
}

package com.askdog.web.configuration.oauth2.clients;

import com.askdog.web.configuration.oauth2.converter.TextOAuth2AccessTokenMessageConverter;
import com.askdog.web.configuration.oauth2.AdOAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;

import java.util.Collections;

public class WeiboClientResources extends DefaultClientResources {

    @Override
    public OAuth2ClientAuthenticationProcessingFilter filter(String path) {
        OAuth2ClientAuthenticationProcessingFilter filter = super.filter(path);

        AdOAuth2RestTemplate restTemplate = (AdOAuth2RestTemplate) filter.restTemplate;
        restTemplate.addMessageConverters(Collections.singletonList(new TextOAuth2AccessTokenMessageConverter()));

        return filter;
    }

}

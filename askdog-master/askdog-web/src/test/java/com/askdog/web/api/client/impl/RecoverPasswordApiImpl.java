package com.askdog.web.api.client.impl;

import com.askdog.web.api.client.RecoverPassword;
import com.askdog.web.api.client.extractor.ResponseEntityExtractor;

import java.net.URI;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

class RecoverPasswordApiImpl extends AbstractApi<RecoverPassword.Api, Void> implements RecoverPassword.Api {

    private static final String API_PASSWORD_RECOVER = "/api/password/recover";

    private String baseUrl;

    RecoverPasswordApiImpl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public ResponseEntityExtractor recover(String mail) {
        URI uri = fromUriString(baseUrl).path(API_PASSWORD_RECOVER)
                .queryParam("mail", mail)
                .build().toUri();
        setEntity(getRestTemplate().postForEntity(uri, null, String.class));
        return this;
    }
}

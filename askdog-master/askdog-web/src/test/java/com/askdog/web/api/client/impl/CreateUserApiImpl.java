package com.askdog.web.api.client.impl;

import com.askdog.web.api.client.CreateUser;
import com.askdog.web.api.client.extractor.ResponseEntityExtractor;
import com.askdog.web.api.client.extractor.ResponseExtractor;
import com.askdog.web.api.vo.RegisterUser;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

class CreateUserApiImpl extends AbstractApi<CreateUser.Action, Void> implements CreateUser.Api {

    private static final String API_USER = "/api/user";
    private static final String API_USER_CONFIRM = "/api/user/confirm";

    private URI userApiUri;
    private UriComponentsBuilder userConfirmApiUriBuilder;

    CreateUserApiImpl(String baseUrl) {
        userApiUri = fromUriString(baseUrl).path(API_USER).build().toUri();
        userConfirmApiUriBuilder = fromUriString(baseUrl).path(API_USER_CONFIRM);
    }

    @Override
    public ResponseEntityExtractor create(RegisterUser user) {
        setEntity(getRestTemplate().postForEntity(userApiUri, user, String.class));
        return this;
    }

    @Override
    public ResponseExtractor confirm(String token) {
        URI uri = userConfirmApiUriBuilder.replaceQueryParam("token", token).build().toUri();
        setEntity(getRestTemplate().putForEntity(uri, null, String.class));
        return this;
    }
}

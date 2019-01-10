package com.askdog.web.api.client.impl;

import com.askdog.web.api.vo.UserSelf;
import com.askdog.web.api.client.Me;
import com.askdog.web.api.client.extractor.ResponseBodyExtractor;

import java.net.URI;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

class MeApiImpl extends AbstractApi<Me.Action, UserSelf> implements Me.Api {

    private static final String API_ME = "/api/me";

    private URI uri;

    MeApiImpl(String baseUrl) {
        uri = fromUriString(baseUrl).path(API_ME).build().toUri();
    }

    @Override
    public ResponseBodyExtractor<UserSelf> me() {
        setEntity(getRestTemplate().getForEntity(uri, String.class));
        return this;
    }

    @Override
    public UserSelf getBody() {
        return getBody(UserSelf.class);
    }
}

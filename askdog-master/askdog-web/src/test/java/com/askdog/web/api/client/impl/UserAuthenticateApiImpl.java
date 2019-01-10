package com.askdog.web.api.client.impl;

import com.askdog.web.api.vo.UserSelf;
import com.askdog.web.api.client.UserAuthenticate;
import com.askdog.web.api.client.extractor.ResponseBodyExtractor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

class UserAuthenticateApiImpl extends AbstractApi<UserAuthenticate.Action, UserSelf> implements UserAuthenticate.Api {

    private static final String URL_LOGIN = "/login";

    private URI loginUri;

    UserAuthenticateApiImpl(String baseUrl) {
        loginUri = fromUriString(baseUrl)
                .path(URL_LOGIN)
                .queryParam("ajax", true)
                .build()
                .toUri();
    }

    @Override
    public ResponseBodyExtractor<UserSelf> authenticate(String username, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        setEntity(getRestTemplate().postFormForEntity(loginUri, map, String.class));
        return this;
    }

    @Override
    public UserSelf getBody() {
        return getBody(UserSelf.class);
    }
}

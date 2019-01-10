package com.askdog.web.api.client.impl;

import com.askdog.web.api.client.extractor.ResponseEntityExtractor;
import com.askdog.web.api.vo.PostedAnswer;
import com.askdog.web.api.vo.PresentationAnswer;

import java.net.URI;

import static com.askdog.web.api.client.CreateAnswer.Action;
import static com.askdog.web.api.client.CreateAnswer.Api;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

class CreateAnswerApiImpl extends AbstractApi<Action, PresentationAnswer> implements Api {

    private static final String API_ANSWER = "/api/answers";

    private URI answerApiUri;

    CreateAnswerApiImpl(String baseUrl) {
        answerApiUri = fromUriString(baseUrl)
                .path(API_ANSWER)
                .build()
                .toUri();
    }

    @Override
    public ResponseEntityExtractor create(PostedAnswer answerVo) {
        setEntity(getRestTemplate().postForEntity(answerApiUri, answerVo, String.class));
        return this;
    }

    @Override
    public PresentationAnswer getBody() {
        return getBody(PresentationAnswer.class);
    }
}

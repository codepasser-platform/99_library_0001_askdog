package com.askdog.web.api.client.impl;

import com.askdog.service.bo.PureQuestion;
import com.askdog.web.api.client.extractor.ResponseBodyExtractor;
import com.askdog.web.api.vo.PresentationQuestion;

import java.net.URI;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

class CreateQuestionApiImpl extends AbstractApi<com.askdog.web.api.client.CreateQuestion.Action, PresentationQuestion> implements com.askdog.web.api.client.CreateQuestion.Api {

    private static final String API_QUESTION = "/api/questions";

    private URI questionApiUri;

    CreateQuestionApiImpl(String baseUrl) {
        questionApiUri = fromUriString(baseUrl)
                .path(API_QUESTION)
                .build()
                .toUri();
    }

    @Override
    public ResponseBodyExtractor<PresentationQuestion> create(PureQuestion pureQuestion) {
        setEntity(getRestTemplate().postForEntity(questionApiUri, pureQuestion, String.class));
        return this;
    }

    @Override
    public PresentationQuestion getBody() {
        return getBody(PresentationQuestion.class);
    }
}

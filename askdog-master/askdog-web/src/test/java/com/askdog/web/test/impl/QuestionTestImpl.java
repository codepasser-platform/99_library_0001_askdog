package com.askdog.web.test.impl;

import com.askdog.service.bo.PureQuestion;
import com.askdog.web.api.client.ApiClient;
import com.askdog.web.api.client.extractor.ResponseExtractor;
import com.askdog.web.api.vo.PresentationQuestion;
import com.askdog.web.test.QuestionTest;
import com.askdog.web.test.impl.inspector.ComposedInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.net.CookieStore;

class QuestionTestImpl implements QuestionTest.Test {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    private ApiClient apiClient;

    private CookieStore cookieStore;

    @Override
    public QuestionTest.Api withCookies(CookieStore store) {
        this.cookieStore = store;
        return this;
    }

    @Override
    public QuestionTest.QuestionInspector<PresentationQuestion> create(PureQuestion question) {
        return new QuestionInspectorImpl<>(beanFactory, apiClient.createQuestionApi().withCookies(cookieStore).create(question));
    }

    private static class QuestionInspectorImpl<T> extends AbstractInspector<T> implements ComposedInspector<T>, QuestionTest.QuestionInspector<T> {
        QuestionInspectorImpl(AutowireCapableBeanFactory beanFactory, ResponseExtractor entityExtractor) {
            super(beanFactory, entityExtractor);
        }
    }
}

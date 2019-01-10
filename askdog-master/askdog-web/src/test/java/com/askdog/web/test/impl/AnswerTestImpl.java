package com.askdog.web.test.impl;

import com.askdog.web.api.client.ApiClient;
import com.askdog.web.api.client.extractor.ResponseExtractor;
import com.askdog.web.api.vo.PostedAnswer;
import com.askdog.web.api.vo.PresentationAnswer;
import com.askdog.web.test.AnswerTest;
import com.askdog.web.test.impl.inspector.ComposedInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.net.CookieStore;

class AnswerTestImpl implements AnswerTest.Test {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    private ApiClient apiClient;

    private CookieStore cookieStore;

    @Override
    public AnswerTest.Api withCookies(CookieStore store) {
        this.cookieStore = store;
        return this;
    }

    @Override
    public AnswerTest.AnswerInspector<PresentationAnswer> create(PostedAnswer answer) {
        return new AnswerInspectorImpl<>(beanFactory, apiClient.createAnswerApi().withCookies(cookieStore).create(answer));
    }

    private static class AnswerInspectorImpl<T> extends AbstractInspector<T> implements ComposedInspector<T>, AnswerTest.AnswerInspector<T> {
        AnswerInspectorImpl(AutowireCapableBeanFactory beanFactory, ResponseExtractor entityExtractor) {
            super(beanFactory, entityExtractor);
        }
    }
}

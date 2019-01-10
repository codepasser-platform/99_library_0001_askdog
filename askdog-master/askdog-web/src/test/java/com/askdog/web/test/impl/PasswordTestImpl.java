package com.askdog.web.test.impl;

import com.askdog.web.api.client.ApiClient;
import com.askdog.web.api.client.extractor.ResponseExtractor;
import com.askdog.web.test.PasswordTest;
import com.askdog.web.test.PasswordTest.PasswordInspector;
import com.askdog.web.test.impl.inspector.ComposedInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.net.CookieStore;

class PasswordTestImpl implements PasswordTest.Test {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    private ApiClient apiClient;

    private CookieStore cookieStore;

    @Override
    public PasswordTest.Api withCookies(CookieStore store) {
        this.cookieStore = store;
        return this;
    }

    @Override
    public PasswordInspector<Void> recover(String mail) {
        return new PasswordInspectorImpl<>(beanFactory, apiClient.recoverPasswordApi().withCookies(cookieStore).recover(mail));
    }

    private static class PasswordInspectorImpl<T> extends AbstractInspector<T> implements ComposedInspector<T>, PasswordInspector<T> {
        PasswordInspectorImpl(AutowireCapableBeanFactory beanFactory, ResponseExtractor entityExtractor) {
            super(beanFactory, entityExtractor);
        }
    }
}

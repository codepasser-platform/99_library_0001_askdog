package com.askdog.web.test.impl;

import com.askdog.web.api.client.extractor.ResponseBodyExtractor;
import com.askdog.web.api.client.extractor.ResponseExtractor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.HttpStatus;

import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_NO;

abstract class AbstractInspector<T> {

    private AutowireCapableBeanFactory beanFactory;

    private ResponseExtractor entityExtractor;

    AbstractInspector(AutowireCapableBeanFactory beanFactory, ResponseExtractor entityExtractor) {
        this.beanFactory = beanFactory;
        this.entityExtractor = entityExtractor;
    }

    public String getResponse() {
        return entityExtractor.getPlainBody();
    }

    public T getBody() {
        if (entityExtractor instanceof ResponseBodyExtractor) {
            //noinspection unchecked
            return ((ResponseBodyExtractor<T>) entityExtractor).getBody();
        }
        return null;
    }

    public HttpStatus getHttpStatus() {
        return entityExtractor.getStatus();
    }

    public void autowire(Object bean) {
        beanFactory.autowireBeanProperties(bean, AUTOWIRE_NO, false);
    }
}

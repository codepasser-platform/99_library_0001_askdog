package com.askdog.web.api.client.impl;

import com.askdog.common.utils.Json;
import com.askdog.web.api.client.extractor.ResponseBodyExtractor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.CookieStore;

import static org.junit.Assert.assertNotNull;

abstract class AbstractApi<A, E> implements ResponseBodyExtractor<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractApi.class);

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseEntity<String> entity;
    private CookieStore cookieStore;

    public A withCookies(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
        //noinspection unchecked
        return (A) this;
    }

    RestTemplate getRestTemplate() {
        return new RestTemplate(objectMapper, cookieStore);
    }

    ResponseEntity<String> getEntity() {
        return entity;
    }

    void setEntity(ResponseEntity<String> entity) {
        LOGGER.debug("API response is: {}", entity.getBody());
        this.entity = entity;
    }

    @Override
    public E getBody() {
        throw new NotImplementedException();
    }

    E getBody(Class<E> type) {
        E result = Json.readValue(getPlainBody(), type);
        assertNotNull(result);
        return result;
    }

    @Override
    public ResponseEntity<String> getResponseEntity() {
        return this.entity;
    }
}

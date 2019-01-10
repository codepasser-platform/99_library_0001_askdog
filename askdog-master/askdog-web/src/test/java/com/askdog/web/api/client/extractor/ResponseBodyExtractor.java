package com.askdog.web.api.client.extractor;

public interface ResponseBodyExtractor<T> extends ResponseEntityExtractor {
    T getBody();
}

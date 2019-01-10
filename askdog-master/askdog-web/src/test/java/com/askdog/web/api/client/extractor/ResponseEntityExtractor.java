package com.askdog.web.api.client.extractor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseEntityExtractor  extends ResponseExtractor {

    ResponseEntity<String> getResponseEntity();

    @Override
    default String getPlainBody() {
        return getResponseEntity().getBody();
    }

    @Override
    default HttpStatus getStatus() {
        return getResponseEntity().getStatusCode();
    }
}

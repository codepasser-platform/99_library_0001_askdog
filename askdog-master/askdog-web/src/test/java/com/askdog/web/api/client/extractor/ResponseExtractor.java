package com.askdog.web.api.client.extractor;

import com.askdog.web.dsl.Chain;
import org.springframework.http.HttpStatus;

public interface ResponseExtractor extends Chain {
    HttpStatus getStatus();
    String getPlainBody();
}

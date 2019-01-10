package com.askdog.web.test.impl.inspector;

import com.askdog.web.test.Inspector;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;

public interface StatusInspector<N extends Inspector> extends Inspector.StatusInspector<N> {

    @Override
    default N expectStatus(HttpStatus status) {
        assertEquals("expect the http status", status, getHttpStatus());
        return afterStatusInspector();
    }

    HttpStatus getHttpStatus();

    N afterStatusInspector();
}

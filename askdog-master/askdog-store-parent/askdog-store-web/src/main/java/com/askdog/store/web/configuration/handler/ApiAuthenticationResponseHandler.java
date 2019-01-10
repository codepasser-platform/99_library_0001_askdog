package com.askdog.store.web.configuration.handler;

import com.askdog.common.exception.Message;
import com.askdog.store.web.HttpEntityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.askdog.store.web.api.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class ApiAuthenticationResponseHandler implements AuthenticationResponseHandler {

    @Autowired
    private HttpEntityProcessor httpEntityProcessor;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ResponseEntity<Message> entity = ResponseEntity.status(FORBIDDEN)
                .contentType(APPLICATION_JSON_UTF8)
                .body(new Message(new AccessException(authException)));

        httpEntityProcessor.writeEntity(entity, response);
    }
}

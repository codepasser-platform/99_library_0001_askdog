package com.askdog.store.web.configuration.handler;

import com.askdog.common.exception.Message;
import com.askdog.store.web.HttpEntityProcessor;
import com.askdog.store.web.api.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkArgument;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class WebAuthenticationFailureHandler implements AuthenticationFailureHandler, AjaxAuthenticationHandler<WebAuthenticationFailureHandler> {

    @Autowired
    private HttpEntityProcessor httpEntityProcessor;

    private String ajaxParam = DEFAULT_AJAX_PARAM;

    private String errorPage;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // redirect previous url
        if (!Boolean.valueOf(request.getParameter(ajaxParam))) {
            // super.onAuthenticationFailed(request, response, authentication);
            if (errorPage != null) {
                // Put exception into request scope (perhaps of use to a view)
                response.setStatus(SC_UNAUTHORIZED);
                request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
                // redirect to error page.
                response.sendRedirect(errorPage);
            } else {
                response.sendError(SC_UNAUTHORIZED, exception.getMessage());
            }
            return;
        }

        // write user info into response
        ResponseEntity<Message> entity = ResponseEntity.status(SC_UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(new Message(new AccessException(exception)));
        httpEntityProcessor.writeEntity(entity, response);
    }

    @Override
    public WebAuthenticationFailureHandler ajaxParam(String name) {
        this.ajaxParam = name;
        return this;
    }

    public WebAuthenticationFailureHandler errorPage(String errorPage) {
        checkArgument(errorPage == null || errorPage.startsWith("/"), "errorPage must begin with '/'");
        this.errorPage = errorPage;
        return this;
    }
}

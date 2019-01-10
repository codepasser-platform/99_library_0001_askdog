package com.askdog.store.web.configuration.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
        implements LogoutSuccessHandler {

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        if (!Boolean.valueOf(request.getParameter(AjaxAuthenticationHandler.DEFAULT_AJAX_PARAM))) {
            super.handle(request, response, authentication);
            return;
        }

        response.setStatus(HttpStatus.OK.value());
        response.flushBuffer();
    }

}

package com.askdog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.askdog.web.api.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/api")
public class TokenApi {

    @Autowired
    private CsrfTokenRepository csrfTokenRepository;

    @PreAuthorize("permitAll")
    @RequestMapping(value = "/csrf/token", produces = APPLICATION_JSON_UTF8_VALUE)
    public CsrfToken csrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = csrfTokenRepository.loadToken(request);
        if (csrfToken == null) {
            csrfToken = csrfTokenRepository.generateToken(request);
        }

        return csrfToken;
    }

}

package com.askdog.store.web.api.annotation;


import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.*;

import static com.askdog.store.web.api.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("isAuthenticated()")
@RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE)
@ResponseStatus(HttpStatus.CREATED)
@ResponseBody
public @interface RestApiPost {

}

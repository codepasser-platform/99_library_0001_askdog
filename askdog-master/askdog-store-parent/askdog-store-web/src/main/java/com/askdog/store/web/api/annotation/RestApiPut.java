package com.askdog.store.web.api.annotation;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

import static com.askdog.store.web.api.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public @interface RestApiPut {

}

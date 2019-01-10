package com.askdog.service.event.annotation;

import com.askdog.model.entity.inner.EventType;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
@Documented
@Inherited
@Component
public @interface EventListener {

    String name();

    EventType type();

    String[] dependOn() default {};

}

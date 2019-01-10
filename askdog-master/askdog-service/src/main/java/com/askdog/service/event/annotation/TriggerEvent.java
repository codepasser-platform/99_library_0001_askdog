package com.askdog.service.event.annotation;

import com.askdog.model.entity.inner.EventType;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD})
@Retention(RUNTIME)
@Documented
@Inherited
@Component
// TODO TriggerEvent can not annotate the RESTful APIs !, just allowed be using in service implementation layer.
public @interface TriggerEvent {

    TriggerEventItem[] value();

    @Retention(RUNTIME)
    @Target({})
    @interface TriggerEventItem {
        String condition() default "true";

        String performerId() default "";

        EventType eventType();

        String targetId() default "";

        String prepare() default "";
    }

}

package com.askdog.service.event.annotation;

public interface ExtraDataCreator<R> {
    R prepare(Object prepareParam);
}

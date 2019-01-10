package com.askdog.service.impl;

import org.springframework.scheduling.annotation.Async;

public interface AsyncCaller {

    @Async
    default void asyncCall(Runnable runnable) {
        runnable.run();
    }

}

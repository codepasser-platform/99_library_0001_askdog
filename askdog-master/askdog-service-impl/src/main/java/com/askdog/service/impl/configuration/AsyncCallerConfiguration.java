package com.askdog.service.impl.configuration;

import com.askdog.service.impl.AsyncCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncCallerConfiguration implements AsyncConfigurer {

    private static Logger logger = LoggerFactory.getLogger(AsyncCallerConfiguration.class);

    @Bean
    public AsyncCaller asyncCaller() {
        return new AsyncCaller() {};
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(7);
        executor.setMaxPoolSize(42);
        executor.setQueueCapacity(11);
        executor.setThreadNamePrefix("async-");
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
                logger.error("can not send mail, error occurs on method [{}], and the exception message is [{}]", method.getName(), throwable.getMessage());
        throwable.printStackTrace();};
    }
}
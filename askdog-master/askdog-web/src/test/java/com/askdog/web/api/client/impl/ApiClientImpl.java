package com.askdog.web.api.client.impl;

import com.askdog.web.api.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_NO;

@Component
public class ApiClientImpl implements ApiClient {

    @Value("http://localhost:${server.port}")
    private String baseUrl;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Override
    public Me.Api meApi() {
        return autowired(new MeApiImpl(baseUrl));
    }

    @Override
    public CreateUser.Api createUserApi() {
        return autowired(new CreateUserApiImpl(baseUrl));
    }

    @Override
    public UserAuthenticate.Api authenticateUserApi() {
        return autowired(new UserAuthenticateApiImpl(baseUrl));
    }

    @Override
    public RecoverPassword.Api recoverPasswordApi() {
        return autowired(new RecoverPasswordApiImpl(baseUrl));
    }

    @Override
    public CreateQuestion.Api createQuestionApi() {
        return autowired(new CreateQuestionApiImpl(baseUrl));
    }

    @Override
    public CreateAnswer.Api createAnswerApi() {
        return autowired(new CreateAnswerApiImpl(baseUrl));
    }

    private <T> T autowired(T instance) {
        beanFactory.autowireBeanProperties(instance, AUTOWIRE_NO, false);
        return instance;
    }

}

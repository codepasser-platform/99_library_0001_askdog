package com.askdog.web.test.impl;

import com.askdog.web.test.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_NO;

@Component
public class TesterImpl implements Tester {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Override
    public UserTest.Test user() {
        return autowired(new UserTestImpl());
    }

    @Override
    public PasswordTest.Test password() {
        return autowired(new PasswordTestImpl());
    }

    @Override
    public QuestionTest.Test question() {
        return autowired(new QuestionTestImpl());
    }

    @Override
    public AnswerTest.Test answer() {
        return autowired(new AnswerTestImpl());
    }

    private <T> T autowired(T instance) {
        beanFactory.autowireBeanProperties(instance, AUTOWIRE_NO, true);
        return instance;
    }
}
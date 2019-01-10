package com.askdog.service.impl.incentive;

import com.askdog.service.exception.incentive.ConfigureException;

import java.util.Map;

public interface ExpressionResolver {
    int resolver(Map<String, Object> params, String expression) throws ConfigureException;
}

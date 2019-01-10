package com.askdog.service.impl.incentive;

import com.askdog.common.utils.SpelUtils;
import com.askdog.model.entity.inner.ExpressionType;
import com.askdog.service.exception.incentive.ConfigureException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static com.askdog.service.exception.incentive.ConfigureException.Error.*;

@Component
public class ExpressionResolverFactory {

    private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

    public ExpressionResolver get(ExpressionType expressionType) throws ConfigureException {
        switch (expressionType) {
            case TEXT:
                return textExpressionResolver();
            case SPEL:
                return spelExpressionResolver();
            case JAVASCRIPT:
                return javascriptResolver();
            case GROOVY:
                return groovyExpressionResolver();
        }

        throw new ConfigureException(UNSUPPORTED_EXPRESSION_TYPE);
    }

    @Bean
    public ExpressionResolver textExpressionResolver() {
        return (params, expression) -> Integer.valueOf(String.valueOf(expression));
    }

    @Bean
    public ExpressionResolver spelExpressionResolver() {
        return (params, expression) -> {
            StandardEvaluationContext simpleContext = new StandardEvaluationContext(params);
            simpleContext.addPropertyAccessor(new MapAccessor());
            return SpelUtils.parse(expression, Integer.class, simpleContext);
        };
    }

    @Bean
    public ExpressionResolver groovyExpressionResolver() {
        return (params, expression) -> {
            try {
                ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("groovy");
                params.forEach(scriptEngine::put);
                return Integer.valueOf(String.valueOf(scriptEngine.eval(expression)));
            } catch (ScriptException e) {
                throw new ConfigureException(GROOVY_RESOLVE_ERROR, e);
            }
        };
    }

    @Bean
    public ExpressionResolver javascriptResolver() {
        return (params, expression) -> {
            try {
                ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");
                params.forEach(scriptEngine::put);
                return Float.valueOf(String.valueOf(scriptEngine.eval(expression))).intValue();
            } catch (ScriptException e) {
                throw new ConfigureException(JAVA_SCRIPT_RESOLVE_ERROR, e);
            }
        };
    }

}

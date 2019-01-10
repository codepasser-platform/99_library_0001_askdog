package com.askdog.model.entity.builder;

import com.askdog.model.entity.IncentivePolicy;
import com.askdog.model.entity.inner.EventType;
import com.askdog.model.entity.inner.ExpressionType;
import com.askdog.model.entity.inner.IncentiveReason;
import com.askdog.model.entity.inner.IncentiveType;

import java.util.Set;

public class IncentivePolicyBuilder {

    private EventType eventType;
    private IncentiveReason incentiveReason;
    private IncentiveType incentiveType;
    private String name;
    private Set<String> triggerNames;
    private ExpressionType expressionType;
    private String expression;

    public static IncentivePolicyBuilder getBuilder() {
        return new IncentivePolicyBuilder();
    }

    public IncentivePolicyBuilder setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public IncentivePolicyBuilder setIncentiveReason(IncentiveReason incentiveReason) {
        this.incentiveReason = incentiveReason;
        return this;
    }

    public IncentivePolicyBuilder setIncentiveType(IncentiveType incentiveType) {
        this.incentiveType = incentiveType;
        return this;
    }

    public IncentivePolicyBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public IncentivePolicyBuilder setTriggerNames(Set<String> triggerNames) {
        this.triggerNames = triggerNames;
        return this;
    }

    public IncentivePolicyBuilder setExpressionType(ExpressionType expressionType) {
        this.expressionType = expressionType;
        return this;
    }

    public IncentivePolicyBuilder setExpression(String expression) {
        this.expression = expression;
        return this;
    }

    public IncentivePolicy build() {
        IncentivePolicy incentivePolicy = new IncentivePolicy();
        incentivePolicy.setEventType(eventType);
        incentivePolicy.setIncentiveReason(incentiveReason);
        incentivePolicy.setIncentiveType(incentiveType);
        incentivePolicy.setName(name);
        incentivePolicy.setExpressionType(expressionType);
        incentivePolicy.setTriggerNames(triggerNames);
        incentivePolicy.setExpression(expression);
        return incentivePolicy;
    }
}

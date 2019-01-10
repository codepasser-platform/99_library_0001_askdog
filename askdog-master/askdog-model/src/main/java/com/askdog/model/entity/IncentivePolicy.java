package com.askdog.model.entity;

import com.askdog.model.entity.inner.EventType;
import com.askdog.model.entity.inner.ExpressionType;
import com.askdog.model.entity.inner.IncentiveReason;
import com.askdog.model.entity.inner.IncentiveType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "ad_incentive_policy")
public class IncentivePolicy extends Base {

    private static final long serialVersionUID = 6248643451507713601L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "incentive_reason")
    private IncentiveReason incentiveReason;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "incentive_type")
    private IncentiveType incentiveType;

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ad_incentive_policy_trigger_names", joinColumns = {
            @JoinColumn(name = "incentive_policy_name", referencedColumnName = "name")
    })
    @Column(name = "trigger_names")
    private Set<String> triggerNames;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "expression_type")
    private ExpressionType expressionType;

    @NotNull
    @Column(name = "expression")
    private String expression;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public IncentiveReason getIncentiveReason() {
        return incentiveReason;
    }

    public void setIncentiveReason(IncentiveReason incentiveReason) {
        this.incentiveReason = incentiveReason;
    }

    public IncentiveType getIncentiveType() {
        return incentiveType;
    }

    public void setIncentiveType(IncentiveType incentiveType) {
        this.incentiveType = incentiveType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getTriggerNames() {
        return triggerNames;
    }

    public void setTriggerNames(Set<String> triggerNames) {
        this.triggerNames = triggerNames;
    }

    public ExpressionType getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(ExpressionType expressionType) {
        this.expressionType = expressionType;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

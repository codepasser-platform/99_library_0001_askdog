package com.askdog.service.event;

import com.askdog.model.entity.inner.EventType;
import com.askdog.service.event.annotation.TriggerEvent;
import com.askdog.service.event.core.Event;
import com.google.common.collect.Maps;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.askdog.common.utils.SpelUtils.parse;
import static com.askdog.service.event.core.EventBuilder.getBuilder;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.elasticsearch.common.Strings.isNullOrEmpty;
import static org.springframework.util.StringUtils.isEmpty;

@Aspect
@Configuration
public class ListenerAspect {

    @Autowired private ListenerRegistry listenerRegistry;
    @Autowired private ExtraDataCreatorRegistry extraDataCreatorRegistry;

    @Around(value = "@annotation(triggerEvent)")
    public Object around(ProceedingJoinPoint pjp, TriggerEvent triggerEvent) throws Throwable {
        Map<EventType, Object> eventPreparedValueCache = prepareEventExtraData(createEvaluationContext(pjp, null), triggerEvent);
        Object returnValue = pjp.proceed();
        List<Event> events = createEvents(createEvaluationContext(pjp, returnValue), triggerEvent);
        for (Event event : events) {
            listenerRegistry.fire(event, eventPreparedValueCache.get(event.getEventType()));
        }
        return returnValue;
    }

    private Map<EventType, Object> prepareEventExtraData(StandardEvaluationContext evaluationContext, TriggerEvent triggerEvent) {
        Map<EventType, Object> eventPreparedValueCache = new HashMap<>();
        stream(triggerEvent.value()).forEach(
                triggerEventItem -> {
                    EventType eventType = triggerEventItem.eventType();
                    boolean condition = parse(triggerEventItem.condition(), Boolean.class, evaluationContext);
                    String prepare = triggerEventItem.prepare();
                    if (condition && !isNullOrEmpty(prepare)) {
                        eventPreparedValueCache.put(eventType, extraDataCreatorRegistry.prepare(eventType, parse(prepare, evaluationContext)));
                    }
                }
        );

        return eventPreparedValueCache;
    }

    private List<Event> createEvents(StandardEvaluationContext evaluationContext, TriggerEvent triggerEvent) {
        return stream(triggerEvent.value()).map(
                triggerEventItem -> {
                    EventType eventType = triggerEventItem.eventType();
                    boolean condition = parse(triggerEventItem.condition(), Boolean.class, evaluationContext);
                    if (condition) {
                        String userId = parse(triggerEventItem.performerId(), String.class, evaluationContext);
                        String targetId = isEmpty(triggerEventItem.targetId()) ? null : parse(triggerEventItem.targetId(), String.class, evaluationContext);
                        return getBuilder()
                                .setPerformerId(userId)
                                .setEventType(eventType)
                                .setTargetId(targetId)
                                .build();
                    }

                    return null;
                }
        ).filter(Objects::nonNull).collect(toList());
    }

    private StandardEvaluationContext createEvaluationContext(ProceedingJoinPoint pjp, Object returnValue) {
        Map<String, Object> params = prepareParams(pjp);
        params.put("returnValue", returnValue);
        StandardEvaluationContext simpleContext = new StandardEvaluationContext(params);
        simpleContext.addPropertyAccessor(new MapAccessor());
        return simpleContext;
    }

    private Map<String, Object> prepareParams(JoinPoint pjp) {
        Map<String, Object> params = Maps.newHashMap();
        String[] parameterNames = ((MethodSignature) pjp.getSignature()).getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            params.put(parameterNames[i], pjp.getArgs()[i]);
        }
        return params;
    }

}

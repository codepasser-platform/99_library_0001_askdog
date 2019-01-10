package com.askdog.service.event.annotation;

import com.askdog.service.event.ListenerRegistry;
import com.askdog.service.event.core.Listener;
import com.askdog.service.exception.ListenerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class EventListenerPostProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EventListenerPostProcessor.class);

    @Autowired
    private ListenerRegistry listenerRegistry;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Listener) {
            Listener listener = (Listener) bean;
            try {
                listenerRegistry.register(listener);
            } catch (ListenerException e) {
                ListenerRegistry.Meta meta = ListenerRegistry.Meta.of(listener);
                logger.error("Cannot register listener :" + meta.getName(), e);
                throw new FatalBeanException("Cannot register listener :" + meta.getName(), e);
            }
        }
        return bean;
    }
}

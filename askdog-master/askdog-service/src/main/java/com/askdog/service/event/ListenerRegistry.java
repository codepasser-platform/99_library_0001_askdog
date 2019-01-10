package com.askdog.service.event;

import com.askdog.model.entity.inner.EventType;
import com.askdog.service.event.annotation.EventListener;
import com.askdog.service.event.core.Event;
import com.askdog.service.event.core.Listener;
import com.askdog.service.exception.ListenerException;
import com.askdog.service.exception.ServiceException;

import javax.annotation.Nonnull;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public interface ListenerRegistry {

    void register(@Nonnull Listener listener) throws ListenerException;

    void register(@Nonnull Listener listener, @Nonnull EventType eventType, @Nonnull String listenerName, Set<String> dependencies) throws ListenerException;

    void fire(@Nonnull Event event) throws ServiceException;

    void fire(@Nonnull Event event, Object extraData) throws ServiceException;

    class Meta {

        private String name;
        private EventType type;
        private Set<String> dependencies;

        public String getName() {
            return name;
        }

        public EventType getType() {
            return type;
        }

        public Set<String> getDependencies() {
            return dependencies;
        }

        public static Meta of(Listener listener) {
            return of(listener.getClass());
        }

        public static Meta of(Class<? extends Listener> listenerClass) {
            EventListener eventListener = listenerClass.getAnnotation(EventListener.class);
            Meta meta = new Meta();
            meta.name = eventListener.name();
            meta.type = eventListener.type();
            meta.dependencies = newHashSet(eventListener.dependOn());
            return meta;
        }
    }
}

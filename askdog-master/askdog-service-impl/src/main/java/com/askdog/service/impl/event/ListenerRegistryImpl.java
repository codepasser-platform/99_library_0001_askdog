package com.askdog.service.impl.event;

import com.askdog.model.data.EventLog;
import com.askdog.model.entity.inner.EventType;
import com.askdog.model.repository.mongo.EventLogRepository;
import com.askdog.service.event.ListenerRegistry;
import com.askdog.service.event.core.Event;
import com.askdog.service.exception.ListenerException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.event.core.EventOwnerIdResolver;
import com.askdog.service.event.core.Listener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.askdog.model.data.builder.EventLogBuilder.getBuilder;
import static com.askdog.service.exception.ListenerException.Error.CYCLIC_DEPENDENCIES;

@Service
public class ListenerRegistryImpl implements ListenerRegistry {

    private static final Logger logger = LoggerFactory.getLogger(ListenerRegistryImpl.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Lazy
    @Autowired private EventLogRepository eventLogRepository;
    @Autowired private EventOwnerIdResolver eventOwnerIdResolver;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<EventType, Set<Listener>> listeners;
    private Map<String, Set<String>> dependencies;

    @PostConstruct
    private void init() {
        this.listeners = new EnumMap<>(EventType.class);
        this.dependencies = new HashMap<>();
    }

    public synchronized void register(@Nonnull Listener listener) throws ListenerException {
        Meta meta = Meta.of(listener);
        register(listener, meta.getType(), meta.getName(), meta.getDependencies());
    }

    public synchronized void register(@Nonnull Listener listener, @Nonnull EventType eventType, @Nonnull String listenerName, Set<String> dependencies) throws ListenerException {
        checkCyclicDependency(listenerName, dependencies);
        registerListener(eventType, listener);
    }

    public void fire(@Nonnull Event event) throws ServiceException {
        fire(event, null);
    }

    public void fire(@Nonnull Event event, Object extraData) throws ServiceException {
        // save event log
        EventLog eventLog = eventLogRepository.save(
                getBuilder()
                        .eventType(event.getEventType())
                        .performerId(event.getPerformerId())
                        .ownerId(eventOwnerIdResolver.resolve(event.getEventType(), event.getTargetId()))
                        .targetId(event.getTargetId())
                        .extraData(extraData)
                        .build()
        );

        // print log
        try {
            logger.info(objectMapper.setDateFormat(dateFormat).writeValueAsString(eventLog));
        } catch (JsonProcessingException e) {
            logger.error("ELK:eventLog to json failure");
        }

        // trigger listener
        Set<Listener> listeners = this.listeners.get(event.getEventType());
        if (listeners != null) {
            //noinspection unchecked
            listeners.forEach(listener -> listener.handle(eventLog));
        }
    }

    private void checkCyclicDependency(@Nonnull String listenerName, Set<String> dependencies) throws ListenerException {
        if (dependencies != null) {
            for (String dependency : dependencies) {
                if (dependency.equals(listenerName)) {
                    throw new ListenerException(CYCLIC_DEPENDENCIES);
                }

                checkCyclicDependency(listenerName, this.dependencies.get(dependency));
            }
        }
    }

    private synchronized void registerListener(@Nonnull EventType eventType, @Nonnull Listener listener) {
        Set<Listener> listeners = this.listeners.get(eventType);
        if (listeners == null) {
            listeners = new HashSet<>();
            this.listeners.put(eventType, listeners);
        }

        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

}

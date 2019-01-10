package com.askdog.service.proxy.event;

import com.askdog.model.entity.inner.EventType;
import com.askdog.service.event.ListenerRegistry;
import com.askdog.service.event.core.Event;
import com.askdog.service.event.core.Listener;
import com.askdog.service.exception.ListenerException;
import com.askdog.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Set;

@Service
public class ListenerRegistryImpl implements ListenerRegistry {

    @Override
    public void register(@Nonnull Listener listener) throws ListenerException {

    }

    @Override
    public void register(@Nonnull Listener listener, @Nonnull EventType eventType, @Nonnull String listenerName, Set<String> dependencies) throws ListenerException {

    }

    @Override
    public void fire(@Nonnull Event event) throws ServiceException {

    }

    @Override
    public void fire(@Nonnull Event event, Object extraData) throws ServiceException {

    }
}

package com.askdog.service.impl.notification;

import com.askdog.model.common.EventType;
import com.askdog.model.common.EventType.EventTypeGroup;
import com.askdog.service.UserService;
import com.askdog.service.bo.event.EventRo;
import com.askdog.service.bo.event.EventUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventHelper {

    @Autowired private UserService userService;

    public EventRo convert(Long performerId, EventType eventType, Long targetId) {

        EventTypeGroup eventTypeGroup = eventType.getEventTypeGroup();

        EventRo eventRo = new EventRo();
        eventRo.setType(eventType);
        eventRo.setUser(new EventUser().setId(performerId).setName(userService.findDetail(performerId).getNickname()));

        // TODO resolve the event target.
        // eventRo.setTarget(target);

        return eventRo;
    }
}
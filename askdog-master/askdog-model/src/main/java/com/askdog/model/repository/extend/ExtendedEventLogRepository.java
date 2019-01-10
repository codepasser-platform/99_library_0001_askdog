package com.askdog.model.repository.extend;

import com.askdog.model.data.EventLog;
import com.askdog.model.entity.inner.EventType;

import java.util.List;

public interface ExtendedEventLogRepository {
    List<EventLog> topTargetByPerformerAndEventType(String performerId, EventType type, long top);
}

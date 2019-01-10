package com.askdog.service;

import com.askdog.model.data.EventLog;
import com.askdog.service.exception.NotFoundException;

public interface EventLogService {
    EventLog findById(String eventId) throws NotFoundException;
}

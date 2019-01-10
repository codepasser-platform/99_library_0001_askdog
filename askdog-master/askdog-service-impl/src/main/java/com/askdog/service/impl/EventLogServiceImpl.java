package com.askdog.service.impl;

import com.askdog.model.data.EventLog;
import com.askdog.model.repository.mongo.EventLogRepository;
import com.askdog.service.EventLogService;
import com.askdog.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.askdog.service.exception.NotFoundException.Error.EVENT_LOG;

@Service
public class EventLogServiceImpl implements EventLogService {

    @Autowired
    private EventLogRepository eventLogRepository;

    @Override
    public EventLog findById(String eventId) throws NotFoundException {
        return eventLogRepository.findById(eventId).orElseThrow(() -> new NotFoundException(EVENT_LOG));
    }
}

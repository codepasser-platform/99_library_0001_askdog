package com.askdog.service.proxy;

import com.askdog.model.data.EventLog;
import com.askdog.service.EventLogService;
import com.askdog.service.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EventLogServiceImpl implements EventLogService {

    @Override
    public EventLog findById(String eventId) throws NotFoundException {
        return null;
    }
}

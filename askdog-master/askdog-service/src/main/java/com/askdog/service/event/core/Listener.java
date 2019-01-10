package com.askdog.service.event.core;

import com.askdog.model.data.EventLog;

public interface Listener {
    void handle(EventLog eventLog);
}

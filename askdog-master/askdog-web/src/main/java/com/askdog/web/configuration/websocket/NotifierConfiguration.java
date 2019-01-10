package com.askdog.web.configuration.websocket;

import com.askdog.model.data.OriginalNotification;
import com.askdog.service.notification.Notifier;
import com.askdog.web.api.helper.NotificationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class NotifierConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(NotifierConfiguration.class);

    @Autowired private Notifier notifier;
    @Autowired private WebSocketManager webSocketManager;
    @Autowired private NotificationHelper notificationHelper;

    @PostConstruct
    public void init() {
        notifier.addObserver((o, originalNotificationArg) -> {

            OriginalNotification originalNotification = (OriginalNotification) originalNotificationArg;

            try {
                webSocketManager.sendData(originalNotification.getRecipient(), notificationHelper.convert(originalNotification));
            } catch (Exception e) {
                logger.error("Error on send data use websocket", e);
            }
        });
    }


}

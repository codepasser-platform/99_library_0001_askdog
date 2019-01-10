package com.askdog.service.notification;

import com.askdog.model.data.OriginalNotification;
import com.askdog.model.repository.mongo.OriginalNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Observable;

@Component
public class Notifier extends Observable {

    @Autowired
    private OriginalNotificationRepository originalNotificationRepository;

    public void notify(OriginalNotification originalNotification) {
        setChanged();
        originalNotificationRepository.save(originalNotification);
        notifyObservers(originalNotification);
    }

}

package com.askdog.web.api.helper;

import com.askdog.model.data.OriginalNotification;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.ServiceException;
import com.askdog.web.api.vo.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationHelper {

    @Autowired private EventHelper eventHelper;
    @Autowired private IncentiveHelper incentiveHelper;

    public Notification convert(OriginalNotification originalNotification) throws ServiceException {
        Notification notification = new Notification();

        notification.setId(originalNotification.getId());
        notification.setNotificationType(originalNotification.getNotificationType());
        notification.setCreateTime(originalNotification.getCreateTime());
        notification.setRead(originalNotification.isRead());

        switch (originalNotification.getNotificationType()) {
            case EVENT:
                notification.setContent(eventHelper.convert(originalNotification.getRefLogId()));
                break;

            case INCENTIVE:
                notification.setContent(incentiveHelper.convert(originalNotification.getRefLogId()));
                break;
        }

        return notification;
    }
}

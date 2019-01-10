package com.askdog.service.impl.event.listener.notification;

import com.askdog.model.data.EventLog;
import com.askdog.model.data.ShareLog;
import com.askdog.model.data.UserLocation;
import com.askdog.model.data.inner.NotificationType;
import com.askdog.model.repository.mongo.ShareLogRepository;
import com.askdog.service.event.annotation.EventListener;
import com.askdog.service.event.core.Listener;
import com.askdog.service.impl.location.LocationRecorder;
import com.askdog.service.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.askdog.model.data.builder.OriginalNotificationBuilder.notificationBuilder;
import static com.askdog.model.entity.inner.EventType.SHARE_QUESTION_NEARBY;

@EventListener(name = "shareQuestion", type = SHARE_QUESTION_NEARBY)
public class ShareListener implements Listener {
    private static Logger logger = LoggerFactory.getLogger(ShareListener.class);

    @Autowired private Notifier notifier;
    @Autowired private ShareLogRepository shareLogRepository;
    @Autowired private LocationRecorder locationRecorder;

    @Override
    public void handle(EventLog event) {
        Optional<ShareLog> shareLogOptional = shareLogRepository.findByUserIdAndTargetId(event.getPerformerId(), event.getTargetId());

        if (!shareLogOptional.isPresent()) {
            logger.error("Target shareLog not found : id = " + event.getTargetId());
            return;
        }

        shareLogOptional.ifPresent(shareLog -> {
            List<UserLocation> nearbyUsers =
                    locationRecorder.findNearbyUsers(shareLog.getUserId(),
                            shareLog.getGeo().getLat(),
                            shareLog.getGeo().getLng());

            nearbyUsers.forEach(nearbyUser ->
                    notifier.notify(notificationBuilder()
                            .notificationType(NotificationType.EVENT)
                            .recipientUserId(nearbyUser.getId())
                            .refLogId(event.getId())
                            .build())
            );
        });
    }
}

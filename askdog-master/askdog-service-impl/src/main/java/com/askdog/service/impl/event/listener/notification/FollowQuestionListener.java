package com.askdog.service.impl.event.listener.notification;

import com.askdog.model.data.EventLog;
import com.askdog.model.data.inner.NotificationType;
import com.askdog.model.entity.Question;
import com.askdog.service.impl.cell.QuestionCell;
import com.askdog.service.event.annotation.EventListener;
import com.askdog.service.event.core.Listener;
import com.askdog.service.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.askdog.model.data.builder.OriginalNotificationBuilder.notificationBuilder;
import static com.askdog.model.entity.inner.EventType.FOLLOW_QUESTION;

@EventListener(name = "followQuestionNotification", type = FOLLOW_QUESTION)
public class FollowQuestionListener implements Listener {
    private static Logger logger = LoggerFactory.getLogger(FollowQuestionListener.class);

    @Autowired private Notifier notifier;
    @Autowired private QuestionCell questionCell;

    @Override
    public void handle(EventLog event) {
        Optional<Question> questionOptional = questionCell.find(event.getTargetId());

        if (!questionOptional.isPresent()) {
            logger.error("Target question not found : id = " + event.getTargetId());
            return;
        }

        notifier.notify(notificationBuilder()
                .notificationType(NotificationType.EVENT)
                .recipientUserId(questionOptional.get().getAuthor().getUuid())
                .refLogId(event.getId())
                .build());
    }
}

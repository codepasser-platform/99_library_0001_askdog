package com.askdog.service.impl.event.listener.notification;

import com.askdog.model.data.EventLog;
import com.askdog.model.data.inner.NotificationType;
import com.askdog.model.entity.Answer;
import com.askdog.service.impl.cell.AnswerCell;
import com.askdog.service.event.annotation.EventListener;
import com.askdog.service.event.core.Listener;
import com.askdog.service.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.askdog.model.data.builder.OriginalNotificationBuilder.notificationBuilder;
import static com.askdog.model.entity.inner.EventType.UP_VOTE_ANSWER;

@EventListener(name = "upVoteQuestionNotification", type = UP_VOTE_ANSWER)
public class UpVoteAnswerListener implements Listener {
    private static Logger logger = LoggerFactory.getLogger(UpVoteAnswerListener.class);

    @Autowired  private Notifier notifier;
    @Autowired private AnswerCell answerCell;

    @Override
    public void handle(EventLog event) {
        Optional<Answer> answerOptional = answerCell.find(event.getTargetId());

        if (!answerOptional.isPresent()) {
            logger.error("Target answer not found : id = " + event.getTargetId());
            return;
        }

        notifier.notify(notificationBuilder()
                .notificationType(NotificationType.EVENT)
                .recipientUserId(answerOptional.get().getAnswerer().getUuid())
                .refLogId(event.getId())
                .build());
    }
}

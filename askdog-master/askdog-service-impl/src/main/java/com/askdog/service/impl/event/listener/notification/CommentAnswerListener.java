package com.askdog.service.impl.event.listener.notification;

import com.askdog.model.data.EventLog;
import com.askdog.model.data.inner.NotificationType;
import com.askdog.model.entity.AnswerComment;
import com.askdog.model.repository.AnswerCommentRepository;
import com.askdog.service.event.annotation.EventListener;
import com.askdog.service.event.core.Listener;
import com.askdog.service.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.askdog.model.data.builder.OriginalNotificationBuilder.notificationBuilder;
import static com.askdog.model.entity.inner.EventType.CREATE_ANSWER_COMMENT;

@EventListener(name = "createAnswerCommentNotification", type = CREATE_ANSWER_COMMENT)
public class CommentAnswerListener implements Listener {
    private static Logger logger = LoggerFactory.getLogger(CommentAnswerListener.class);

    @Autowired private Notifier notifier;
    @Autowired private AnswerCommentRepository answerCommentRepository;

    @Override
    public void handle(EventLog event) {
        AnswerComment comment = answerCommentRepository.findOne(event.getTargetId());

        if (comment == null) {
            logger.error("Target answer comment not found : id = " + event.getTargetId());
            return;
        }

        notifier.notify(notificationBuilder()
                .notificationType(NotificationType.EVENT)
                .recipientUserId(comment.getAnswer().getAnswerer().getUuid())
                .refLogId(event.getId())
                .build());
    }
}

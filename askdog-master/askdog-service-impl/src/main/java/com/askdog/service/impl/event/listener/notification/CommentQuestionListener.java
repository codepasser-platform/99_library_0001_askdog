package com.askdog.service.impl.event.listener.notification;

import com.askdog.model.data.EventLog;
import com.askdog.model.data.inner.NotificationType;
import com.askdog.model.entity.QuestionComment;
import com.askdog.model.repository.QuestionCommentRepository;
import com.askdog.service.event.annotation.EventListener;
import com.askdog.service.event.core.Listener;
import com.askdog.service.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.askdog.model.data.builder.OriginalNotificationBuilder.notificationBuilder;
import static com.askdog.model.entity.inner.EventType.CREATE_QUESTION_COMMENT;

@EventListener(name = "createQuestionCommentNotification", type = CREATE_QUESTION_COMMENT)
public class CommentQuestionListener implements Listener {
    private static Logger logger = LoggerFactory.getLogger(CommentQuestionListener.class);

    @Autowired private Notifier notifier;
    @Autowired private QuestionCommentRepository questionCommentRepository;

    @Override
    public void handle(EventLog event) {
        QuestionComment comment = questionCommentRepository.findOne(event.getTargetId());

        if (comment == null) {
            logger.error("Target question comment not found : id = " + event.getTargetId());
            return;
        }

        notifier.notify(notificationBuilder()
                .notificationType(NotificationType.EVENT)
                .recipientUserId(comment.getQuestion().getAuthor().getUuid())
                .refLogId(event.getId())
                .build());
    }
}

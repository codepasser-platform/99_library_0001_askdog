package com.askdog.service.impl.event.listener.notification;

import com.askdog.model.data.EventLog;
import com.askdog.model.data.inner.NotificationType;
import com.askdog.model.entity.Answer;
import com.askdog.model.entity.Question;
import com.askdog.model.entity.User;
import com.askdog.service.impl.cell.AnswerCell;
import com.askdog.service.impl.cell.QuestionCell;
import com.askdog.service.event.annotation.EventListener;
import com.askdog.service.event.core.Listener;
import com.askdog.service.impl.mail.FollowerNewAnswerNotificationMail;
import com.askdog.service.impl.mail.NewAnswerNotificationMail;
import com.askdog.service.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.askdog.model.data.builder.OriginalNotificationBuilder.notificationBuilder;
import static com.askdog.model.entity.inner.EventType.CREATE_ANSWER;
import static com.askdog.service.impl.mail.NewAnswerNotificationMail.VAR_QUESTION;
import static com.google.common.collect.ImmutableMap.of;

@EventListener(name = "createAnswerNotification", type = CREATE_ANSWER)
public class AnswerListener implements Listener {
    private static Logger logger = LoggerFactory.getLogger(AnswerListener.class);

    @Autowired private Notifier notifier;
    @Autowired private AnswerCell answerCell;
    @Autowired private QuestionCell questionCell;
    @Autowired private NewAnswerNotificationMail newAnswerNotificationMail;
    @Autowired private FollowerNewAnswerNotificationMail followerNewAnswerNotificationMail;

    @Override
    public void handle(EventLog event) {
        Optional<Answer> answerOptional = answerCell.find(event.getTargetId());

        if (!answerOptional.isPresent()) {
            logger.error("Target answer not found : id = " + event.getTargetId());
            return;
        }

        Answer answer = answerOptional.get();
        Question question = answer.getQuestion();
        User questionAuthor = question.getAuthor();
        User answerer = answer.getAnswerer();
        notifier.notify(notificationBuilder()
                .notificationType(NotificationType.EVENT)
                .recipientUserId(questionAuthor.getUuid())
                .refLogId(event.getId())
                .build());

        questionCell.forEachFollowers(question.getUuid(), user -> {
            if (!answerer.equals(user)) {
                if (user.equals(questionAuthor)) {
                    newAnswerNotificationMail.send(user, of(VAR_QUESTION, question));
                } else {
                    followerNewAnswerNotificationMail.send(user, of(VAR_QUESTION, question));
                }
            }
        });
    }
}

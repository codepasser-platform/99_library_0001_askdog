package com.askdog.service.impl.mail;

import com.askdog.common.FreemarkerTemplate;
import com.askdog.common.TemplateSource;
import com.askdog.model.entity.Question;
import com.askdog.model.entity.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

@Component
public class NewAnswerNotificationMail extends AbstractNotificationMail {

    public static final String VAR_QUESTION = "question";

    @TemplateSource(name = "notification-question-answer")
    private FreemarkerTemplate template;

    @Override
    FreemarkerTemplate getTemplate() {
        return template;
    }

    @Override
    void updateTemplateData(User user, Map<String, Serializable> data) {
        checkArgument(data.containsKey(VAR_QUESTION), "[question] parameter must be set for template");
        checkArgument(data.get(VAR_QUESTION) instanceof Question, "[question] parameter must be " + Question.class.getName());

        super.updateTemplateData(user, data);

        Question question = (Question) data.get(VAR_QUESTION);
        String url = getMailConfiguration().getUrl().replace("{questionId}", question.getUuid());
        data.put("url", url);
    }
}

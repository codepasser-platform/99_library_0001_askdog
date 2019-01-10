package com.askdog.service.impl.mail;

import com.askdog.common.FreemarkerTemplate;
import com.askdog.common.TemplateSource;
import org.springframework.stereotype.Component;

@Component
public class FollowerNewAnswerNotificationMail extends NewAnswerNotificationMail {

    @TemplateSource(name = "notification-answer-followed-question")
    private FreemarkerTemplate template;

    @Override
    FreemarkerTemplate getTemplate() {
        return template;
    }

}

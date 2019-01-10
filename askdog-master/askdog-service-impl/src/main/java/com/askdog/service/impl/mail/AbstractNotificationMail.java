package com.askdog.service.impl.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

public abstract class AbstractNotificationMail extends AbstractMail {

    @Autowired
    @Qualifier("notification")
    private MailConfiguration mailConfiguration;

    @Override
    MailConfiguration getMailConfiguration() {
        return mailConfiguration;
    }

    @Bean(name = "notification")
    @ConfigurationProperties(prefix = "askdog.mail.notification")
    MailConfiguration mailConfiguration() {
        return new MailConfiguration();
    }

}

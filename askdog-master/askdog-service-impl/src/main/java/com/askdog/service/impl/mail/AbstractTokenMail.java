package com.askdog.service.impl.mail;

import com.askdog.model.entity.User;
import com.askdog.model.repository.TokenRepository;
import com.askdog.service.exception.IllegalArgumentException;
import com.askdog.service.exception.ServiceException;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

import static com.askdog.service.exception.IllegalArgumentException.Error.INVALID_TOKEN;
import static com.google.common.collect.ImmutableMap.of;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

abstract class AbstractTokenMail extends AbstractMail implements TokenMail {

    private static final String KEY_MAIL = "mail";
    private static final String KEY_ISSUE_TIME = "issue_time";
    private static final String PARAM_TOKEN = "token";

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void redeemToken(String mail, String token) throws ServiceException {
        Map<String, String> values = tokenRepository.redeemToken(getTokenName(), token);
        if (!Objects.equal(mail, values.get(KEY_MAIL))) {
            throw new IllegalArgumentException(INVALID_TOKEN);
        }
    }

    @Override
    public String redeemToken(String token) throws ServiceException {
        Map<String, String> values = tokenRepository.redeemToken(getTokenName(), token);
        if (!values.containsKey(KEY_MAIL)) {
            throw new IllegalArgumentException(INVALID_TOKEN);
        }

        return values.get(KEY_MAIL);
    }

    @Override
    public boolean isTokenValidate(String token) {
        return tokenRepository.isTokenValidate(getTokenName(), token);
    }

    @Override
    MailConfiguration getMailConfiguration() {
        return getMailTokenConfiguration();
    }

    @Override
    void updateTemplateData(User user, Map<String, Serializable> data) {
        super.updateTemplateData(user, data);
        String url = fromUriString(getMailTokenConfiguration().getUrl())
                .queryParam(PARAM_TOKEN, claimToken(user))
                .build()
                .toUriString();
        data.put("url", url);
    }

    private String claimToken(User user) {
        Map<String, String> values = of(
                KEY_MAIL, user.getEmail(),
                KEY_ISSUE_TIME, Instant.now().toString()
        );

        return tokenRepository.claimToken(user.getName(), getTokenName(), values,
                getMailTokenConfiguration().getTokenTimeout(),
                getMailTokenConfiguration().getTokenTimeoutUnit()
        );
    }

    abstract String getTokenName();

    abstract MailTokenConfiguration getMailTokenConfiguration();

}

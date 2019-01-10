package com.askdog.web.test.impl;

import com.askdog.model.entity.User;
import com.askdog.model.repository.UserRepository;
import com.askdog.model.repository.mongo.OriginalNotificationRepository;
import com.askdog.web.api.client.ApiClient;
import com.askdog.web.api.client.extractor.ResponseExtractor;
import com.askdog.web.api.vo.RegisterUser;
import com.askdog.web.api.vo.UserSelf;
import com.askdog.web.test.UserTest;
import com.askdog.web.test.UserTest.Api;
import com.askdog.web.test.UserTest.UserInspector;
import com.askdog.web.test.impl.inspector.ComposedInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.net.CookieStore;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.askdog.web.test.cleanup.CleanupExecutorRegistry.register;

class UserTestImpl implements UserTest.Test {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OriginalNotificationRepository originalNotificationRepository;

    private CookieStore cookieStore;

    @Override
    public Api withCookies(CookieStore store) {
        cookieStore = store;
        return this;
    }

    @Override
    public UserInspector<UserSelf> me() {
        return new UserInspectorImpl<>(beanFactory, apiClient.meApi().withCookies(cookieStore).me());
    }

    @Override
    public UserInspector<Void> create(RegisterUser user) {
        registerCleanupExecutors(user);
        return new UserInspectorImpl<>(beanFactory, apiClient.createUserApi().withCookies(cookieStore).create(user));
    }

    @Override
    public UserInspector<User> authenticate(String username, String password) {
        return new UserInspectorImpl<>(beanFactory, apiClient.authenticateUserApi().withCookies(cookieStore).authenticate(username, password));
    }

    @Override
    public UserInspector<Void> confirm(String token) {
        return new UserInspectorImpl<>(beanFactory, apiClient.createUserApi().withCookies(cookieStore).confirm(token));
    }

    private void registerCleanupExecutors(RegisterUser user) {
        register(() -> {
            Optional<User> foundUser = userRepository.findByName(user.getName());
            if (!foundUser.isPresent()) {
                foundUser = userRepository.findByEmail(user.getMail());
            }

            if (foundUser.isPresent()) {
                User u = foundUser.get();
                originalNotificationRepository.delete(
                        originalNotificationRepository.findAll().stream()
                                .filter(originalNotification -> Objects.equals(originalNotification.getRecipient(), u.getUuid()))
                                .collect(Collectors.toList())
                );
                userRepository.delete(u);
            }
        });
    }

    private static class UserInspectorImpl<T> extends AbstractInspector<T> implements ComposedInspector<T>, UserInspector<T> {
        UserInspectorImpl(AutowireCapableBeanFactory beanFactory, ResponseExtractor entityExtractor) {
            super(beanFactory, entityExtractor);
        }
    }
}

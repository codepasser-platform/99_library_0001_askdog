package com.askdog.web.dataset;

import com.askdog.model.entity.User;
import com.askdog.model.repository.UserRepository;
import com.askdog.web.api.client.ApiClient;
import com.askdog.web.api.vo.RegisterUser;
import com.askdog.web.test.DataSet;
import com.askdog.web.test.cleanup.CleanupExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.askdog.web.test.json.RegisterUserBuilder.registerUserBuilder;
import static java.lang.String.format;
import static java.util.stream.Stream.iterate;

public class RegisteredUser implements DataSet {

    public static final String PASSWORD = "12345678";

    private static final AtomicInteger index = new AtomicInteger();
    private static final ThreadLocal<List<RegisterUser>> createdUsers = new ThreadLocal<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private ApiClient apiClient;

    @Override
    public void prepare() {
        List<RegisterUser> users = iterate(0, i -> ++i).limit(2)
                .map(i -> "user_" + index.incrementAndGet())
                .map(name -> registerUserBuilder()
                        .name(name)
                        .mail(name + "@ask.dog")
                        .password(PASSWORD)
                        .build()
                )
                .map(user -> {
                    apiClient.createUserApi().create(user);
                    apiClient.createUserApi().confirm(findToken(user));
                    return user;
                })
                .collect(Collectors.toList());
        createdUsers.set(users);
    }

    @Override
    public CleanupExecutor cleanupExecutor() {
        return () -> {
            createdUsers.get().stream().forEach(
                    user -> {
                        Optional<User> foundUser = userRepository.findByName(user.getName());
                        if (foundUser.isPresent()) {
                            User realUser = foundUser.get();
                            userRepository.delete(realUser);
                        }
                    }
            );
            createdUsers.remove();
        };
    }

    public static List<RegisterUser> getRegisteredUsers() {
        return createdUsers.get().stream().map(user -> registerUserBuilder()
                .name(user.getName())
                .mail(user.getMail())
                .password(user.getPassword())
                .build()
        ).collect(Collectors.toList());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static RegisterUser anyRegisteredUser() {
        return getRegisteredUsers().stream().findAny().get();
    }

    private String findToken(RegisterUser user) {
        try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        HashOperations<String, String, String> hashOperations = template.opsForHash();
        return hashOperations.get(format("token:owners:%s", user.getName()), "registration.confirm");
    }

}

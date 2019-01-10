package com.askdog.web;

import com.askdog.Application;
import com.askdog.web.api.vo.RegisterUser;
import com.askdog.web.dataset.RegisteredUser;
import com.askdog.web.test.Tester;
import com.askdog.web.test.UsingDataSet;
import com.askdog.web.test.cleanup.CleanupTestDataListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static com.askdog.web.dataset.RegisteredUser.anyRegisteredUser;
import static com.askdog.web.test.json.RegisterUserBuilder.registerUserBuilder;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@TestExecutionListeners(listeners = CleanupTestDataListener.class, mergeMode = MERGE_WITH_DEFAULTS)
public class CreateUserApiTest {

    @Autowired
    private Tester tester;

    @Autowired
    private StringRedisTemplate template;

    @Test
    public void shouldCreateUser() {
        tester.user()
                .create(
                        registerUserBuilder()
                                .name("user01")
                                .mail("user01@ask.dog")
                                .password("user password")
                                .build()
                )
                .expectStatus(CREATED);
    }

    @Test
    public void shouldConfirmUserRegistration() throws InterruptedException {
        tester.user()
                .create(
                        registerUserBuilder()
                                .name("user01")
                                .mail("user01@ask.dog")
                                .password("user password")
                                .build()
                )
                .expectStatus(CREATED);

        Optional<String> foundToken = tryFindToken("user01");
        assertTrue("the token for user registration should be issued.", foundToken.isPresent());
        tester.user()
                .confirm(foundToken.get())
                .expectStatus(OK);
    }

    @Test
    public void shouldFailedCreateWhenInvalidUserName() {
        tester.user()
                .create(
                        registerUserBuilder()
                                .name("user'1000")
                                .mail("user01@ask.dog")
                                .password("user password")
                                .build()
                )
                .expectStatus(BAD_REQUEST)
                .expectErrorCode("Pattern.registerUser.name");
    }

    @Test
    public void shouldFailedCreateWhenInvalidEmail() {
        tester.user()
                .create(
                        registerUserBuilder()
                                .name("user01")
                                .mail("user01ask.dog")
                                .password("user password")
                                .build()
                )
                .expectStatus(BAD_REQUEST)
                .expectErrorCode("Pattern.registerUser.mail");
    }

    @Test
    public void shouldFailedCreateWhenPasswordTooShort() {
        tester.user()
                .create(
                        registerUserBuilder()
                                .name("user01")
                                .mail("user01@ask.dog")
                                .password("1")
                                .build()
                )
                .expectStatus(BAD_REQUEST)
                .expectErrorCode("Size.registerUser.password");
    }

    @Test
    public void shouldFailedCreateWhenPasswordTooLong() {
        tester.user()
                .create(
                        registerUserBuilder()
                                .name("user01")
                                .mail("user01@ask.dog")
                                .password("123456789012345678901")
                                .build()
                )
                .expectStatus(BAD_REQUEST)
                .expectErrorCode("Size.registerUser.password");
    }

    @Test
    public void shouldFailedCreateWhenMissingUserName() {
        tester.user()
                .create(
                        registerUserBuilder()
                                .mail("user01@ask.dog")
                                .password("user password")
                                .build()
                )
                .expectStatus(BAD_REQUEST)
                .expectErrorCode("NotNull.registerUser.name");
    }

    @Test
    public void shouldFailedCreateWhenMissingEmail() {
        tester.user()
                .create(
                        registerUserBuilder()
                                .name("user01")
                                .password("user password")
                                .build()
                )
                .expectStatus(BAD_REQUEST)
                .expectErrorCode("NotNull.registerUser.mail");
    }

    @Test
    public void shouldFailedCreateWhenMissingPassword() {
        tester.user()
                .create(
                        registerUserBuilder()
                                .name("user01")
                                .mail("user01@ask.dog")
                                .build()
                )
                .expectStatus(BAD_REQUEST)
                .expectErrorCode("NotNull.registerUser.password");
    }

    @Test
    @UsingDataSet(RegisteredUser.class)
    public void shouldFailedCreateWhenDuplicated() {

        // create this user again
        RegisterUser user = anyRegisteredUser();
        tester.user()
                .create(user)
                .expectStatus(CONFLICT)
                .expectErrorCode("SRV_CONFLICT_USER_NAME");

        // change the user name, and register again
        user = anyRegisteredUser();
        user.setName(user.getName() + "_modified");
        tester.user()
                .create(user)
                .expectStatus(CONFLICT)
                .expectErrorCode("SRV_CONFLICT_USER_MAIL");

        // change the user mail, and register again
        user = anyRegisteredUser();
        user.setMail(user.getMail() + "_modified");
        tester.user()
                .create(user)
                .expectStatus(CONFLICT)
                .expectErrorCode("SRV_CONFLICT_USER_NAME");
    }

    private Optional<String> tryFindToken(String username) throws InterruptedException {
        Thread.sleep(100);
        HashOperations<String, String, String> hashOperations = template.opsForHash();
        return ofNullable(hashOperations.get(format("token:owners:%s", username), "registration.confirm"));
    }

}

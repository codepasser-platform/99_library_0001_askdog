package com.askdog.web;

import com.askdog.Application;
import com.askdog.web.api.vo.RegisterUser;
import com.askdog.web.api.vo.UserSelf;
import com.askdog.web.dataset.AuthenticatedUser;
import com.askdog.web.dataset.RegisteredUser;
import com.askdog.web.test.Tester;
import com.askdog.web.test.UsingDataSet;
import com.askdog.web.test.cleanup.CleanupTestDataListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.askdog.model.entity.inner.user.UserType.REGISTERED;
import static com.askdog.web.dataset.AuthenticatedUser.getAuthenticatedUserSelf;
import static com.askdog.web.dataset.AuthenticatedUser.getCookieStore;
import static com.askdog.web.dataset.RegisteredUser.PASSWORD;
import static com.askdog.web.dataset.RegisteredUser.anyRegisteredUser;
import static com.askdog.web.test.matcher.ReturnValueMatchers.describedAs;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@TestExecutionListeners(listeners = CleanupTestDataListener.class, mergeMode = MERGE_WITH_DEFAULTS)
public class UserAuthenticateApiTest {

    @Autowired
    private Tester tester;

    @Test
    @UsingDataSet(RegisteredUser.class)
    public void shouldConfirmRegistrationSuccessful() {
        RegisterUser user = anyRegisteredUser();

        // login with user name
        tester.user()
                .authenticate(user.getName(), PASSWORD)
                .expectStatus(OK);

        // login with mail address
        tester.user()
                .authenticate(user.getMail(), PASSWORD)
                .expectStatus(OK);
    }

    @Test
    @UsingDataSet(RegisteredUser.class)
    public void shouldAuthenticateSuccessful() {
        RegisterUser user = anyRegisteredUser();

        // login with user name
        tester.user()
                .authenticate(user.getName(), PASSWORD)
                .expectStatus(OK);

        // login with mail address
        tester.user()
                .authenticate(user.getMail(), PASSWORD)
                .expectStatus(OK);
    }

    @Test
    public void shouldAuthenticateFailedWhenUserNotExists() {
        tester.user()
                .authenticate("not_existed_user", "password")
                .expectStatus(UNAUTHORIZED)
                .expectErrorCode("org.springframework.security.authentication.BadCredentialsException");
    }

    @Test
    @UsingDataSet(RegisteredUser.class)
    public void shouldAuthenticateFailedWhenUserNameAndPasswordNotMatch() {
        RegisterUser user = anyRegisteredUser();

        // login with user name and wrong password
        tester.user()
                .authenticate(user.getName(), "wrong_password")
                .expectStatus(UNAUTHORIZED)
                .expectErrorCode("org.springframework.security.authentication.BadCredentialsException");

        // login with mail address and wrong password
        tester.user()
                .authenticate(user.getMail(), "wrong_password")
                .expectStatus(UNAUTHORIZED)
                .expectErrorCode("org.springframework.security.authentication.BadCredentialsException");

        // login with nonexistent user name and password
        tester.user()
                .authenticate("nonexistent_user", PASSWORD)
                .expectStatus(UNAUTHORIZED)
                .expectErrorCode("org.springframework.security.authentication.BadCredentialsException");

        // login with nonexistent user mail address and password
        tester.user()
                .authenticate("nonexistent_mail", PASSWORD)
                .expectStatus(UNAUTHORIZED)
                .expectErrorCode("org.springframework.security.authentication.BadCredentialsException");
    }

    @Test
    @UsingDataSet({RegisteredUser.class, AuthenticatedUser.class})
    public void shouldGetUserInfoSuccessfulAfterAuthenticated() {
        UserSelf userSelf = getAuthenticatedUserSelf();
        tester.user()
                .withCookies(getCookieStore())
                .me()
                .expectStatus(OK)
                .expect(describedAs(
                        "name", equalTo(userSelf.getName()),
                        "mail", equalTo(userSelf.getMail()),
                        "type", equalTo(REGISTERED)
                ));

    }

}

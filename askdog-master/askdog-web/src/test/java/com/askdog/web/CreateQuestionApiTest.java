package com.askdog.web;

import com.askdog.Application;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.askdog.model.entity.builder.ContentBuilder.textContentBuilder;
import static com.askdog.model.entity.inner.Type.TEXT;
import static com.askdog.web.test.json.PureQuestionBuilder.createQuestionBuilder;
import static com.askdog.web.test.matcher.ReturnValueMatchers.describedAs;
import static java.util.Locale.SIMPLIFIED_CHINESE;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@TestExecutionListeners(listeners = CleanupTestDataListener.class, mergeMode = MERGE_WITH_DEFAULTS)
public class CreateQuestionApiTest {

    @Autowired
    private Tester tester;

    @Test
    @UsingDataSet({RegisteredUser.class, AuthenticatedUser.class})
    public void shouldCreateQuestion() {
        // TODO we should make clean chain for data cleanup.
        tester.question()
                .withCookies(AuthenticatedUser.getCookieStore())
                .create(
                        createQuestionBuilder()
                                .subject("I have a question")
                                .content(textContentBuilder().content("How make the tests more easier than before ?"))
                                .labels("q&a", "question", "label")
                                .build()
                )
                .expectStatus(HttpStatus.CREATED)
                .expect(describedAs(
                        "uuid", notNullValue(),
                        "subject", equalTo("I have a question"),
                        "content.type", equalTo(TEXT),
                        "author", notNullValue(),
                        "language", equalTo(SIMPLIFIED_CHINESE),
                        "creationTime", notNullValue(),
                        "labels", containsInAnyOrder("q&a", "question", "label")
                ));
    }

    @Test
    @UsingDataSet({RegisteredUser.class, AuthenticatedUser.class})
    public void shouldCreateQuestionWhenMissingLabels() {
        tester.question()
                .withCookies(AuthenticatedUser.getCookieStore())
                .create(
                        createQuestionBuilder()
                                .subject("I have a question")
                                .content(textContentBuilder().content("How make the tests more easier than before ?"))
                                .build()
                )
                .expectStatus(HttpStatus.CREATED)
                .expect(describedAs(
                        "uuid", notNullValue(),
                        "subject", equalTo("I have a question"),
                        "content.type", equalTo(TEXT),
                        "author", notNullValue(),
                        "language", equalTo(SIMPLIFIED_CHINESE),
                        "creationTime", notNullValue(),
                        "labels", nullValue()
                ));
    }

    @Test
    public void shouldFailedCreateWhenNotAuthenticated() {
        tester.question()
                .create(
                        createQuestionBuilder()
                                .subject("I have a question")
                                .content(textContentBuilder().content("How make the tests more easier than before ?"))
                                .labels("q&a", "question", "label")
                                .build()
                )
                .expectStatus(HttpStatus.FORBIDDEN)
                .expectErrorCode("org.springframework.security.authentication.InsufficientAuthenticationException");
    }

    @Test
    public void shouldFailedCreateWhenMissingSubject() {
        tester.question()
                .create(
                        createQuestionBuilder()
                                .content(textContentBuilder().content("How make the tests more easier than before ?"))
                                .labels("q&a", "question", "label")
                                .build()
                )
                .expectStatus(HttpStatus.BAD_REQUEST)
                .expectErrorCode("NotNull.createQuestion.subject");
    }

    @Test
    public void shouldFailedCreateWhenMissingContent() {
        tester.question()
                .create(
                        createQuestionBuilder()
                                .subject("I have a question")
                                .labels("q&a", "question", "label")
                                .build()
                )
                .expectStatus(HttpStatus.BAD_REQUEST)
                .expectErrorCode("NotNull.createQuestion.content");
    }

}

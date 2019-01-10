package com.askdog.web;

import com.askdog.Application;
import com.askdog.model.data.inner.NotificationType;
import com.askdog.model.entity.StorageLink;
import com.askdog.web.dataset.AuthenticatedUser;
import com.askdog.web.dataset.CreatedQuestion;
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

import static com.askdog.model.data.builder.OriginalNotificationBuilder.notificationBuilder;
import static com.askdog.model.entity.builder.ContentBuilder.pictureContentBuilder;
import static com.askdog.model.entity.builder.ContentBuilder.textContentBuilder;
import static com.askdog.model.entity.inner.Type.PIC;
import static com.askdog.model.entity.inner.Type.TEXT;
import static com.askdog.web.dataset.AuthenticatedUser.getAuthenticatedUser;
import static com.askdog.web.dataset.CreatedQuestion.getCreatedQuestion;
import static com.askdog.web.test.json.CreateAnswerBuilder.createAnswerBuilder;
import static com.askdog.web.test.matcher.EntityMatchers.picturesInStatus;
import static com.askdog.web.test.matcher.NotificationMatchers.notified;
import static com.askdog.web.test.matcher.ReturnValueMatchers.describedAs;
import static java.util.Locale.SIMPLIFIED_CHINESE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@TestExecutionListeners(listeners = CleanupTestDataListener.class, mergeMode = MERGE_WITH_DEFAULTS)
public class CreateAnswerApiTest {

    @Autowired
    private Tester tester;

    @Test
    @UsingDataSet({RegisteredUser.class, AuthenticatedUser.class, CreatedQuestion.class})
    public void shouldCreateAnswer() {
        tester.answer()
                .withCookies(AuthenticatedUser.getCookieStore())
                .create(
                        createAnswerBuilder()
                                .questionId(getCreatedQuestion().getId())
                                .content(textContentBuilder().content("I am answering this question ..."))
                                .build()
                )
                .expectStatus(CREATED)
                .expect(describedAs(
                        "uuid", notNullValue(),
                        "question", equalTo(getCreatedQuestion().getId()),
                        "answerer", equalTo(getAuthenticatedUser().getUuid()),
                        "content.type", equalTo(TEXT),
                        "content.content", equalTo("I am answering this question ..."),
                        "language", equalTo(SIMPLIFIED_CHINESE)
                ));
    }

    @Test
    @UsingDataSet({RegisteredUser.class, AuthenticatedUser.class, CreatedQuestion.class})
    public void shouldCreateAnswerInPictureType() {
        tester.answer()
                .withCookies(AuthenticatedUser.getCookieStore())
                .create(
                        createAnswerBuilder()
                                .questionId(getCreatedQuestion().getId())
                                .content(pictureContentBuilder()
                                        .addPicture("id_1", "open your browser")
                                        .addPicture("id_2", "type the url ask.dog")
                                        .addPicture("id_3", "ask your question")
                                        .content()
                                )
                                .build()
                )
                .expectStatus(CREATED)
                .expect(describedAs(
                        "uuid", notNullValue(),
                        "question", equalTo(getCreatedQuestion().getId()),
                        "answerer", equalTo(getAuthenticatedUser().getUuid()),
                        "content.type", equalTo(PIC),
                        "content.content(id_1)", equalTo("open your browser"),
                        "content.content(id_2)", equalTo("type the url ask.dog"),
                        "content.content(id_3)", equalTo("ask your question"),
                        "language", equalTo(SIMPLIFIED_CHINESE)
                ))
                .expect(picturesInStatus(StorageLink.Status.IN_USE, "id_1", "id_2", "id_3"));
    }

    @Test
    @UsingDataSet({RegisteredUser.class, AuthenticatedUser.class, CreatedQuestion.class})
    public void shouldFailedCreateAnswerWhenNotAuthenticated() {
        tester.answer()
                .create(
                        createAnswerBuilder()
                                .questionId(getCreatedQuestion().getId())
                                .content(textContentBuilder().content("I am answering this question ..."))
                                .build()
                )
                .expectStatus(FORBIDDEN);
    }

    @Test
    @UsingDataSet({RegisteredUser.class, AuthenticatedUser.class, CreatedQuestion.class})
    public void shouldFailedCreateAnswerWhenMissingQuestionId() {
        tester.answer()
                .withCookies(AuthenticatedUser.getCookieStore())
                .create(
                        createAnswerBuilder()
                                .content(textContentBuilder().content("I am answering this question ..."))
                                .build()
                )
                .expectStatus(BAD_REQUEST)
                .expectErrorCode("NotNull.postedAnswer.questionId");


    }

    @Test
    @UsingDataSet({RegisteredUser.class, AuthenticatedUser.class, CreatedQuestion.class})
    public void shouldFailedCreateAnswerWhenMissingContent() {
        tester.answer()
                .withCookies(AuthenticatedUser.getCookieStore())
                .create(
                        createAnswerBuilder()
                                .questionId(getCreatedQuestion().getId())
                                .build()
                )
                .expectStatus(BAD_REQUEST)
                .expectErrorCode("NotNull.postedAnswer.content");
    }

}

package com.askdog.web.dataset;

import com.askdog.model.repository.QuestionRepository;
import com.askdog.web.api.client.ApiClient;
import com.askdog.web.api.vo.PresentationQuestion;
import com.askdog.web.test.DataSet;
import com.askdog.web.test.cleanup.CleanupExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;

import static com.askdog.model.entity.builder.ContentBuilder.textContentBuilder;
import static com.askdog.web.test.json.PureQuestionBuilder.createQuestionBuilder;

public class CreatedQuestion implements DataSet {

    private static final ThreadLocal<PresentationQuestion> dataHolder = new ThreadLocal<>();

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void prepare() {
        PresentationQuestion createdQuestion = apiClient.createQuestionApi()
                .withCookies(AuthenticatedUser.getCookieStore())
                .create(
                        createQuestionBuilder()
                                .subject("I have a question")
                                .content(textContentBuilder().content("How make the tests more easier than before ?"))
                                .build()
                )
                .getBody();

        dataHolder.set(createdQuestion);
    }

    @Nullable
    @Override
    public CleanupExecutor cleanupExecutor() {
        return dataHolder::remove;
    }

    public static PresentationQuestion getCreatedQuestion() {
        return dataHolder.get();
    }
}

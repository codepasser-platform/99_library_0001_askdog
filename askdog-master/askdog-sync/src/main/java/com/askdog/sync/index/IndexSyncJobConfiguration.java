package com.askdog.sync.index;

import com.askdog.sync.index.step.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IndexSyncJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private NewQuestionReader newQuestionReader;

    @Autowired
    private DeleteQuestionReader deleteQuestionReader;

    @Autowired
    private ActiveQuestionReader activeQuestionReader;

    @Autowired
    private ActiveAnswerReader activeAnswerReader;

    @Autowired
    private QuestionIndexWriter questionIndexWriter;

    @Autowired
    private QuestionIndexUpdateWriter questionIndexUpdateWriter;

    @Autowired
    private QuestionIndexDeleteWriter questionIndexDeleteWriter;

    @Bean
    public Job processNewQuestions() {
        return jobBuilderFactory.get("syncNewQuestions")
                .incrementer(new RunIdIncrementer())
                // .listener(listener())
                .flow(syncDeleteQuestion())
                .next(syncNewQuestion())
                .next(syncActiveAnswer())
                .next(syncActiveQuestion())
                .end()
                .build();
    }

    private Step syncNewQuestion() {
        return stepBuilderFactory.get("newQuestion")
                .<Object, QuestionIndex>chunk(10)
                .reader(newQuestionReader)
                .writer(questionIndexWriter)
                .build();
    }

    private Step syncDeleteQuestion() {
        return stepBuilderFactory.get("deleteQuestion")
                .<Object, QuestionIndex>chunk(10)
                .reader(deleteQuestionReader)
                .writer(questionIndexDeleteWriter)
                .build();
    }

    private Step syncActiveQuestion() {
        return stepBuilderFactory.get("activeQuestion")
                .<Object, QuestionIndex>chunk(10)
                .reader(activeQuestionReader)
                .writer(questionIndexUpdateWriter)
                .build();
    }

    private Step syncActiveAnswer() {
        return stepBuilderFactory.get("activeAnswer")
                .<Object, QuestionIndex>chunk(10)
                .reader(activeAnswerReader)
                .writer(questionIndexUpdateWriter)
                .build();
    }

}

package com.askdog.sync.index.step;

import com.askdog.service.bo.QuestionDetail;
import com.askdog.service.impl.cell.QuestionCell;
import com.askdog.sync.index.JobContext;
import com.askdog.sync.index.QuestionIndex;
import com.askdog.sync.service.SyncDataService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

import static com.askdog.model.entity.inner.EventType.*;
import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Sets.union;

@Component
@StepScope
public class ActiveAnswerReader extends AbstractItemReader implements ItemReader<QuestionIndex> {

    @Value("#{jobParameters['date_from']}")
    private Date from;

    @Value("#{jobParameters['date_to']}")
    private Date to;

    @Autowired
    private JobContext jobContext;

    @Autowired
    private SyncDataService syncDataService;

    @Autowired
    private QuestionCell questionCell;

    @PostConstruct
    private void init() {
        setQuestions(
                syncDataService.findQuestions(syncDataService.findTargets(from, to,
                        UPDATE_ANSWER,
                        CREATE_ANSWER,
                        UP_VOTE_ANSWER,
                        DOWN_VOTE_ANSWER,
                        FAV_ANSWER,
                        DELETE_ANSWER
                ), union(jobContext.getNewQuestionIds(), jobContext.getDeleteQuestionIds()), question -> {
                    jobContext.addQuestionIdForActiveAnswer(question.getUuid());
                    return toQuestionIndex(question);
                })
        );
    }

    @Override
    public QuestionIndex toQuestionIndex(QuestionDetail question) {
        String questionId = question.getUuid();

        QuestionIndex questionIndex = new QuestionIndex();
        questionIndex.setId(questionId);
        questionIndex.setAnswerCount(question.getAnswers().size());
        questionIndex.setFullAnswerContent(on(' ').skipNulls().join(getAnswerContents(question)));

        QuestionIndex.Answer bestAnswer = toIndexAnswer(question.getBestAnswer());
        questionIndex.setBestAnswer(bestAnswer);
        questionIndex.setAnswerScore(bestAnswer != null ? bestAnswer.getScore() : null);

        QuestionIndex.Answer newestAnswer = findNewestAnswer(questionId);
        questionIndex.setNewestAnswer(newestAnswer);

        questionIndex.setHotScore(questionCell.calculateScore(question));
        return questionIndex;
    }
}

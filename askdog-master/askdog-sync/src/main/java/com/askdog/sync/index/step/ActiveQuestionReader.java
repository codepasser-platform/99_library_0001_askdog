package com.askdog.sync.index.step;

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
import java.util.Set;

import static com.askdog.model.entity.inner.EventType.*;

@Component
@StepScope
public class ActiveQuestionReader extends AbstractItemReader implements ItemReader<QuestionIndex> {

    @Value("#{jobParameters['date_from']}")
    private Date from;

    @Value("#{jobParameters['date_to']}")
    private Date to;

    @Autowired
    private JobContext jobContext;

    @Autowired
    private SyncDataService syncDataService;

    @PostConstruct
    private void init() {
        Set<String> questionIds = syncDataService.findTargets(from, to,
                UPDATE_QUESTION,
                UP_VOTE_QUESTION,
                DOWN_VOTE_QUESTION,
                FOLLOW_QUESTION,
                UN_FOLLOW_QUESTION
        );
        questionIds.removeIf(questionId -> jobContext.containsInNewQuestionIds(questionId)
                || jobContext.containsForActiveAnswers(questionId)
                || jobContext.containsInDeleteQuestionIds(questionId));
        setQuestions(
                syncDataService.findQuestions(questionIds, question -> {
                    jobContext.addQuestionIdForActiveQuestion(question.getUuid());
                    return toQuestionIndex(question);
                })
        );
    }
}

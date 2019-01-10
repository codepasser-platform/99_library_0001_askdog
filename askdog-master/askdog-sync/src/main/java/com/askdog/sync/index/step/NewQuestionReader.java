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
import java.util.Iterator;
import java.util.Set;

import static com.askdog.model.entity.inner.EventType.CREATE_QUESTION;
import static com.askdog.model.entity.inner.EventType.SHARE_EXPERIENCE;

@Component
@StepScope
public class NewQuestionReader extends AbstractItemReader implements ItemReader<QuestionIndex> {

    @Value("#{jobParameters['date_from']}")
    private Date from;

    @Value("#{jobParameters['date_to']}")
    private Date to;

    @Autowired
    private JobContext jobContext;

    @Autowired
    private SyncDataService syncDataService;

    private Iterator<QuestionIndex> questions;

    @PostConstruct
    private void init() {
        Set<String> questionIds = syncDataService.findTargets(from, to,
                CREATE_QUESTION,
                SHARE_EXPERIENCE);

        questionIds.removeIf(questionId -> jobContext.containsInNewQuestionIds(questionId)
                || jobContext.containsInDeleteQuestionIds(questionId));

        jobContext.addNewQuestionIds(questionIds);
        questions = syncDataService.findQuestions(questionIds, this::toQuestionIndex);
    }

    @Override
    public QuestionIndex read() throws Exception {
        if (questions.hasNext()) {
            return questions.next();
        }

        return null;
    }
}

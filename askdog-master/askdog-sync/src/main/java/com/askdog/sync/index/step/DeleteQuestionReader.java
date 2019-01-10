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

import static com.askdog.model.entity.inner.EventType.DELETE_QUESTION;

@Component
@StepScope
public class DeleteQuestionReader extends AbstractItemReader implements ItemReader<QuestionIndex> {

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
        Set<String> questionIds = syncDataService.findTargets(from, to, DELETE_QUESTION);
        jobContext.addDeleteQuestionIds(questionIds);
        setQuestions(questionIds.stream().map(this::toQuestionIndex).iterator());
    }

    private QuestionIndex toQuestionIndex(String questionId) {
        QuestionIndex questionIndex = new QuestionIndex();
        questionIndex.setId(questionId);
        return questionIndex;
    }
}

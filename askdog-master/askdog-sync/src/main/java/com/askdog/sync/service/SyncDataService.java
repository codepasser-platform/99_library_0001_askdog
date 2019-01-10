package com.askdog.sync.service;

import com.askdog.model.data.EventLog;
import com.askdog.model.entity.inner.EventType;
import com.askdog.model.repository.AnswerRepository;
import com.askdog.service.bo.QuestionDetail;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.cell.QuestionCell;
import com.askdog.sync.index.QuestionIndex;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static com.askdog.sync.service.CriteriaHelper.timeCriteria;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
@Transactional
public class SyncDataService {

    @Autowired
    private QuestionCell questionCell;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Set<String> findTargets(Date from, Date to, EventType ... eventTypes) {
        List<String> eventTypeNames = stream(eventTypes).map(EventType::name).collect(toList());
        Aggregation aggregation = newAggregation(
                match(timeCriteria("eventTime", from, to).and("eventType").in(eventTypeNames)),
                group("targetId")
                        .first("targetId").as("value")
                        .push("extraData").as("extras")
        );

        return aggregate(aggregation, EventLog.class, TargetID.class).stream()
                .map(targetId -> targetId.getExtras() != null && !targetId.getExtras().isEmpty() ? targetId.getExtras().get(0) : targetId.getValue())
                .collect(toSet());
    }

    public Iterator<QuestionIndex> findQuestions(Iterable<String> answerIds, Set<String> excludeQuestionIds, Function<QuestionDetail, QuestionIndex> mapper) {
        List<String> questionIds = stream(answerRepository.findAll(answerIds).spliterator(), false)
                .filter(item -> !excludeQuestionIds.contains(item.getQuestion().getUuid()))
                .map(answer -> answer.getQuestion().getUuid())
                .collect(toList());

        return findQuestions(questionIds, mapper);
    }

    public Iterator<QuestionIndex> findQuestions(Iterable<String> questionIds, Function<QuestionDetail, QuestionIndex> mapper) {
        return Lists.newArrayList(questionIds).stream().<QuestionDetail>map(questionId -> {
            try {
                return questionCell.findDetailStateLess(questionId);

            } catch (ServiceException e) {
                throw new RuntimeException("Cannot get questionDetail[id=" + questionId + "]", e);
            }
        }).map(mapper).collect(toList()).iterator();
    }

    private <T> List<T> aggregate(Aggregation aggregation, Class<?> documentType, Class<T> outputType) {
        String collectionName = mongoTemplate.getCollectionName(documentType);
        return mongoTemplate.aggregate(aggregation, collectionName, outputType).getMappedResults();
    }

    public static class TargetID {

        private String value;
        private List<String> extras;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<String> getExtras() {
            return extras;
        }

        public void setExtras(List<String> extras) {
            this.extras = extras;
        }
    }

}

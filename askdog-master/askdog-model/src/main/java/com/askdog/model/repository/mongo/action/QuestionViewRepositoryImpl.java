package com.askdog.model.repository.mongo.action;

import com.askdog.model.data.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class QuestionViewRepositoryImpl implements QuestionViewRepositoryExtention {

    private static final String COLLECTION_NAME_QUESTION_VIEW = "questionView";

    @Autowired private MongoTemplate mongoTemplate;

    @Override public Page<Actions.QuestionView> findByUser(String userId, Pageable pageable) {
        AggregationResults<Actions.QuestionView> questionViews = mongoTemplate.aggregate(newAggregation(
                match(Criteria.where("user").is(userId)),
                sort(pageable.getSort()),
                group("target")
                        .first("target").as("target")
                        .first("targetType").as("targetType")
                        .first("user").as("user")
                        .first("time").as("time"),
                sort(pageable.getSort()),
                Aggregation.skip(pageable.getOffset()),
                Aggregation.limit(pageable.getPageSize())
        ), COLLECTION_NAME_QUESTION_VIEW, Actions.QuestionView.class);

        AggregationResults<Map> countMap = mongoTemplate.aggregate(newAggregation(
                match(Criteria.where("user").is(userId)),
                group("target").first("user").as("user"),
                group("user").count().as("count")
        ), COLLECTION_NAME_QUESTION_VIEW, Map.class);

        long resultCount = countMap.getUniqueMappedResult() == null ? 0 : Long.valueOf(String.valueOf(countMap.getUniqueMappedResult().get("count")));

        return new PageImpl<>(questionViews.getMappedResults(), pageable, resultCount);
    }

}

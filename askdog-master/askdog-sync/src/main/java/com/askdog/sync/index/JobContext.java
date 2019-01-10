package com.askdog.sync.index;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@JobScope
public class JobContext {

    private Set<String> newQuestionIds = new HashSet<>();
    private Set<String> deleteQuestionIds = new HashSet<>();
    private Set<String> questionIdForActiveQuestions = new HashSet<>();
    private Set<String> questionIdForActiveAnswers = new HashSet<>();

    public Set<String> getNewQuestionIds() {
        return newQuestionIds;
    }

    public Set<String> getDeleteQuestionIds() {
        return deleteQuestionIds;
    }

    public void addNewQuestionIds(Set<String> newQuestionIds) {
        this.newQuestionIds.addAll(newQuestionIds);
    }

    public void addDeleteQuestionIds(Set<String> deleteQuestionIds) {
        this.deleteQuestionIds.addAll(deleteQuestionIds);
    }

    public void addQuestionIdForActiveAnswer(String questionId) {
        this.questionIdForActiveAnswers.add(questionId);
    }

    public void addQuestionIdForActiveQuestion(String questionId) {
        this.questionIdForActiveQuestions.add(questionId);
    }

    public boolean containsInNewQuestionIds(String questionId) {
        return newQuestionIds.contains(questionId);
    }

    public boolean containsInDeleteQuestionIds(String questionId) {
        return deleteQuestionIds.contains(questionId);
    }

    public boolean containsForActiveAnswers(String questionId) {
        return questionIdForActiveAnswers.contains(questionId);
    }

    public boolean containsForActiveQuestions(String questionId) {
        return questionIdForActiveQuestions.contains(questionId);
    }

}

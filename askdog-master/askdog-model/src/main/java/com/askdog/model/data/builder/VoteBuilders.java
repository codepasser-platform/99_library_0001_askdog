package com.askdog.model.data.builder;

import com.askdog.model.data.Actions.AnswerVote;
import com.askdog.model.data.Actions.QuestionVote;
import com.askdog.model.data.inner.VoteDirection;

public class VoteBuilders {

    public static QuestionVoteBuilder questionVoteBuilder() {
        return new QuestionVoteBuilder();
    }

    public static AnswerVoteBuilder answerVoteBuilder() {
        return new AnswerVoteBuilder();
    }

    public static class QuestionVoteBuilder extends ActionBuilder<QuestionVoteBuilder, QuestionVote> {

        private VoteDirection direction;

        public QuestionVoteBuilder direction(VoteDirection direction) {
            this.direction = direction;
            return this;
        }

        public QuestionVote build() {
            QuestionVote questionVote = build(new QuestionVote());
            questionVote.setDirection(this.direction);
            return questionVote;
        }

    }

    public static class AnswerVoteBuilder extends ActionBuilder<AnswerVoteBuilder, AnswerVote> {

        private VoteDirection direction;
        private String question;

        public AnswerVoteBuilder direction(VoteDirection direction) {
            this.direction = direction;
            return this;
        }

        public AnswerVoteBuilder question(String question) {
            this.question = question;
            return this;
        }

        public AnswerVote build() {
            AnswerVote answerVote = build(new AnswerVote());
            answerVote.setDirection(this.direction);
            answerVote.setQuestion(this.question);
            return answerVote;
        }

    }
}

package com.askdog.model.data;

import com.askdog.model.data.inner.VoteDirection;

import javax.validation.constraints.NotNull;
import java.util.Date;

public interface Actions {

    // share
    class QuestionShare extends Action {}

    class AnswerShare extends AnswerAction {}

    // vote
    class QuestionVote extends Action {

        private VoteDirection direction;

        public VoteDirection getDirection() {
            return direction;
        }

        public void setDirection(VoteDirection direction) {
            this.direction = direction;
        }

    }

    class AnswerVote extends AnswerAction {

        private VoteDirection direction;

        public VoteDirection getDirection() {
            return direction;
        }

        public void setDirection(VoteDirection direction) {
            this.direction = direction;
        }
    }

    // follow
    class QuestionFollow extends Action {}

    // favorite
    class AnswerFavorite extends AnswerAction {}

    // view
    class QuestionView extends Action {}

    // abstract supper action definition
    abstract class Action extends Target {

        private String user;

        @NotNull
        private Date time;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }
    }

    abstract class AnswerAction extends Action {

        private String question;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

    }
}

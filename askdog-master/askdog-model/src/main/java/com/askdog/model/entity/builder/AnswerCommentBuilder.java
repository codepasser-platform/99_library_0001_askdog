package com.askdog.model.entity.builder;

import com.askdog.model.entity.Answer;
import com.askdog.model.entity.AnswerComment;
import com.askdog.model.entity.User;
import org.springframework.util.StringUtils;

import java.util.Date;

public final class AnswerCommentBuilder {

    private String content;
    private User commenter;
    private Date creationTime;
    private Answer answer;

    public static AnswerCommentBuilder answerCommentBuilder() {
        return new AnswerCommentBuilder();
    }

    public AnswerCommentBuilder content(String content) {
        this.content = content;
        return this;
    }

    public AnswerCommentBuilder commenter(String commenterId) {
        User commenter = new User();
        commenter.setUuid(commenterId);
        this.commenter = commenter;
        return this;
    }

    public AnswerCommentBuilder creationTime(Date creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public AnswerCommentBuilder answer(String answerId) {
        Answer answer = new Answer();
        answer.setUuid(answerId);
        this.answer = answer;
        return this;
    }

    public AnswerComment build() {
        AnswerComment answerComment = new AnswerComment();
        answerComment.setContent(this.content);
        answerComment.setCommenter(this.commenter);
        answerComment.setCreationTime(this.creationTime);
        answerComment.setAnswer(this.answer);
        return answerComment;
    }
}

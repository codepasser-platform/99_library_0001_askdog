package com.askdog.model.entity.builder;

import com.askdog.model.entity.Question;
import com.askdog.model.entity.QuestionComment;
import com.askdog.model.entity.User;
import org.springframework.util.StringUtils;

import java.util.Date;

public final class QuestionCommentBuilder {

    private String content;
    private User commenter;
    private Date creationTime;
    private Question question;

    public static QuestionCommentBuilder questionCommentBuilder() {
        return new QuestionCommentBuilder();
    }

    public QuestionCommentBuilder content(String content) {
        this.content = content;
        return this;
    }

    public QuestionCommentBuilder commenter(String commenterId) {
        User commenter = new User();
        commenter.setUuid(commenterId);
        this.commenter = commenter;
        return this;
    }

    public QuestionCommentBuilder creationTime(Date creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public QuestionCommentBuilder question(String questionId) {
        Question question = new Question();
        question.setUuid(questionId);
        this.question = question;
        return this;
    }

    public QuestionComment build() {
        QuestionComment questionComment = new QuestionComment();
        questionComment.setContent(this.content);
        questionComment.setCommenter(this.commenter);
        questionComment.setCreationTime(this.creationTime);
        questionComment.setQuestion(this.question);
        return questionComment;
    }
}

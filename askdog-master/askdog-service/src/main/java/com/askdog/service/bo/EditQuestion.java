package com.askdog.service.bo;

import com.askdog.common.Out;
import com.askdog.model.entity.Label;
import com.askdog.model.entity.inner.Content;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

public class EditQuestion implements Out<EditQuestion, QuestionDetail> {

    private String id;

    private String subject;

    private Content content;

    private Set<String> labels;

    private boolean anonymous;

    private boolean share;

    private boolean follow;

    private EditAbleShareAnswer shareAnswer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public EditAbleShareAnswer getShareAnswer() {
        return shareAnswer;
    }

    public void setShareAnswer(EditAbleShareAnswer shareAnswer) {
        this.shareAnswer = shareAnswer;
    }

    @Override
    public EditQuestion from(QuestionDetail entity) {
        this.id = entity.getUuid();
        this.subject = entity.getSubject();
        this.content = entity.getContent();
        this.labels = entity.getLabels();
        this.anonymous = entity.isAnonymous();
        this.share = entity.isExperience();
        this.follow = entity.isFollowed();

        if (entity.isExperience()) {
            entity.getAnswers().stream()
                    .filter(answerDetail -> answerDetail.getAnswerer().getUuid().equals(entity.getAuthor().getUuid()))
                    .findFirst()
                    .ifPresent(answerDetail -> {
                        EditAbleShareAnswer shareAnswer = new EditAbleShareAnswer();
                        shareAnswer.setId(answerDetail.getUuid());
                        shareAnswer.setContent(answerDetail.getContent());
                        this.setShareAnswer(shareAnswer);
                    });
        }

        return this;
    }

    public static class EditAbleShareAnswer {

        private String id;

        @NotNull
        private Content content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }
    }
}

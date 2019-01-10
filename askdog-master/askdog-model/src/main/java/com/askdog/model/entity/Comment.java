package com.askdog.model.entity;

import com.askdog.model.validation.Group;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@MappedSuperclass
public abstract class Comment extends Base implements Cloneable {

    private static final long serialVersionUID = 1060498845201403326L;

    @NotNull
    @Pattern(regexp = "([\\s\\S]{1,220})")
    @Column(name = "content", nullable = false, length = 220)
    private String content;

    @NotNull(groups = {Group.Create.class, Group.Edit.class})
    @JoinColumn(name = "commenter", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private User commenter;

    @NotNull(groups = {Group.Create.class, Group.Edit.class})
    @Column(name = "creation_time", nullable = false, updatable = false)
    private Date creationTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @SuppressWarnings("unchecked")
    public <T> T shallowClone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}

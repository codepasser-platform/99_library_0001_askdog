package com.askdog.model.entity;

import com.askdog.model.entity.inner.user.UserProvider;

import javax.persistence.*;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "ad_external_user")
public class ExternalUser extends Base {

    private static final long serialVersionUID = 702492398661752105L;

    @Column(name = "external_user_id", nullable = false, updatable = false)
    private String externalUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, updatable = false)
    private UserProvider provider;

    @JoinColumn(name="bind_user")
    @ManyToOne(optional = false, cascade = {PERSIST, MERGE})
    private User user;

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public UserProvider getProvider() {
        return provider;
    }

    public void setProvider(UserProvider provider) {
        this.provider = provider;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
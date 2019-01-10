package com.askdog.model.entity;

import com.askdog.model.converter.UserStatusSetConverter;
import com.askdog.model.entity.inner.user.UserStatus;
import com.askdog.model.entity.inner.user.UserType;
import com.askdog.model.validation.Group.Create;
import com.askdog.model.validation.UserIdentifier;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import static com.askdog.common.RegexPattern.*;
import static javax.persistence.CascadeType.REMOVE;

@Entity
@Table(name = "ad_user")
@UserIdentifier(groups = Create.class)
public class User extends Base {

    private static final long serialVersionUID = 3164890844368170505L;

    @NotNull(groups = Create.class)
    @Size(min = 3, max = 20)
    @Pattern(regexp = REGEX_USER_NAME)
    @Column(name = "name", unique = true)
    private String name;

    @NotNull(groups = Create.class)
    @Column(name = "password")
    private String password;

    @Pattern(regexp = REGEX_PHONE)
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Pattern(regexp = REGEX_MAIL)
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(groups = Create.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private UserType type;

    @Column(name = "points")
    private int points;

    @Column(name = "exp")
    private int exp;

    @Convert(converter = UserStatusSetConverter.class)
    @Column(name = "user_statuses")
    private EnumSet<UserStatus> userStatuses;

    @Column(name = "last_access_time")
    private Date lastAccessTime;

    @Column(name = "registration_time")
    private Date registrationTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = REMOVE, mappedBy = "author")
    private List<Question> questions;

    @JoinColumn(name = "avatar")
    @ManyToOne
    private StorageLink avatar;

    @Transient
    private String avatarUrl;

    @Transient
    private String nickName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public EnumSet<UserStatus> getUserStatuses() {
        return userStatuses;
    }

    public void setUserStatuses(EnumSet<UserStatus> userStatuses) {
        this.userStatuses = userStatuses;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addStatus(UserStatus userStatus) {
        if (userStatuses == null) {
            userStatuses = EnumSet.noneOf(UserStatus.class);
        }

        userStatuses.add(userStatus);
    }

    public boolean hasStatus(UserStatus userStatus) {
        return userStatuses != null && userStatuses.contains(userStatus);
    }

    public StorageLink getAvatar() {
        return avatar;
    }

    public void setAvatar(StorageLink avatar) {
        this.avatar = avatar;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
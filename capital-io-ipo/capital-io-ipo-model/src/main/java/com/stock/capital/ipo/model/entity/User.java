package com.stock.capital.ipo.model.entity;

import static com.stock.capital.common.model.RegexPattern.REGEX_GENERAL_NAME;
import static com.stock.capital.common.model.RegexPattern.REGEX_MAIL;
import static com.stock.capital.common.model.RegexPattern.REGEX_PHONE;
import static com.stock.capital.common.model.RegexPattern.REGEX_USER_NAME;
import static javax.persistence.EnumType.STRING;

import com.stock.capital.common.model.entity.Base;
import com.stock.capital.common.model.validation.Group;
import com.stock.capital.ipo.model.entity.converter.UserStatusSetConverter;
import com.stock.capital.ipo.model.entity.inner.UserStatus;
import com.stock.capital.ipo.model.entity.inner.UserType;
import com.stock.capital.ipo.model.validation.UserIdentifier;
import java.util.Date;
import java.util.EnumSet;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * [User entity].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
// http://stackoverflow.com/questions/13012584/jpa-how-to-convert-a-native-query-result-set-to
// -pojo-class-collection
@Entity
@Table(name = "sys_user")
@UserIdentifier(groups = Group.Create.class)
public class User extends Base {

  private static final long serialVersionUID = -8716040457579080960L;

  @NotNull(groups = Group.Create.class)
  @Pattern(regexp = REGEX_USER_NAME)
  @Column(name = "username", unique = true, length = 20)
  private String username;

  @NotNull(groups = Group.Create.class)
  @Column(name = "password", length = 20)
  private String password;

  @Pattern(regexp = REGEX_PHONE)
  @Column(name = "phone_number", unique = true, length = 20)
  private String phoneNumber;

  @Pattern(regexp = REGEX_MAIL)
  @Column(name = "email", unique = true, length = 50)
  private String email;

  @NotNull(groups = Group.Create.class)
  @Enumerated(STRING)
  @Column(name = "type", nullable = false, length = 20)
  private UserType type;

  @Convert(converter = UserStatusSetConverter.class)
  @Column(name = "user_statuses")
  private EnumSet<UserStatus> userStatuses;

  @Column(name = "last_access_time")
  private Date lastAccessTime;

  @Column(name = "registration_time")
  private Date registrationTime;

  @Pattern(regexp = REGEX_GENERAL_NAME)
  @Column(name = "nickname", length = 30)
  private String nickname;

  @Column(name = "avatar")
  private Long avatar;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
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

  public EnumSet<UserStatus> getUserStatuses() {
    return userStatuses;
  }

  public void setUserStatuses(EnumSet<UserStatus> userStatuses) {
    this.userStatuses = userStatuses;
  }

  public void addStatus(UserStatus userStatus) {
    if (userStatuses == null) {
      userStatuses = EnumSet.noneOf(UserStatus.class);
    }
    userStatuses.add(userStatus);
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

  public Long getAvatar() {
    return avatar;
  }

  public void setAvatar(Long avatar) {
    this.avatar = avatar;
  }
}

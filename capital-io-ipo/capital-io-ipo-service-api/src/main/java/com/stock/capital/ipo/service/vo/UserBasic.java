package com.stock.capital.ipo.service.vo;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stock.capital.common.model.Out;
import com.stock.capital.ipo.model.entity.User;
import java.util.Date;
import org.springframework.beans.BeanUtils;

/**
 * [UserBasic].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class UserBasic implements Out<UserBasic, User> {

  private static final long serialVersionUID = -7906184063602029210L;

  @JsonFormat(shape = STRING)
  private Long id;

  private String username;
  private String nickname;
  private String phoneNumber;
  private String email;

  // @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date lastAccessTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date registrationTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  @Override
  public UserBasic from(User entity) {
    BeanUtils.copyProperties(entity, this);
    return this;
  }
}

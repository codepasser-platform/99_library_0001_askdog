package com.stock.capital.ipo.service.vo;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stock.capital.common.business.category.Gender;
import com.stock.capital.common.business.info.Address;
import com.stock.capital.common.model.Out;
import com.stock.capital.common.model.entity.inner.State;
import com.stock.capital.ipo.model.entity.User;
import com.stock.capital.ipo.model.entity.inner.UserType;
import org.springframework.beans.BeanUtils;

/**
 * [UserDetail].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class UserDetail implements Out<UserDetail, User> {

  private static final long serialVersionUID = 2654072793251453099L;

  @JsonFormat(shape = STRING)
  private Long id;

  private String username;
  private String nickname;
  private String phoneNumber;
  private String email;

  private UserType type;

  @JsonFormat(shape = STRING)
  private Long avatar;

  private State state;

  private String realName;

  private Gender gender;

  private Address address;

  private String occupation;

  private String school;

  private String major;

  private String signature;

  private boolean isUpdateUserBasicInfo;

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

  public UserType getType() {
    return type;
  }

  public void setType(UserType type) {
    this.type = type;
  }

  public Long getAvatar() {
    return avatar;
  }

  public void setAvatar(Long avatar) {
    this.avatar = avatar;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public boolean isUpdateUserBasicInfo() {
    return isUpdateUserBasicInfo;
  }

  public void setUpdateUserBasicInfo(boolean updateUserBasicInfo) {
    isUpdateUserBasicInfo = updateUserBasicInfo;
  }

  @Override
  public UserDetail from(User entity) {
    BeanUtils.copyProperties(entity, this);
    return this;
  }
}

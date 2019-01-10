package com.stock.capital.ipo.model.entity.builder;

import com.stock.capital.ipo.model.entity.User;
import com.stock.capital.ipo.model.entity.inner.UserStatus;
import com.stock.capital.ipo.model.entity.inner.UserType;
import java.util.Date;

public final class UserBuilder {

  private String username;
  private String password;
  private String phoneNumber;
  private String email;
  private UserType type;
  private Date registrationTime;

  public static UserBuilder userBuilder() {
    return new UserBuilder();
  }

  public UserBuilder username(String username) {
    this.username = username;
    return this;
  }

  public UserBuilder email(String email) {
    this.email = email;
    return this;
  }

  public UserBuilder phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public UserBuilder password(String password) {
    this.password = password;
    return this;
  }

  public UserBuilder type(UserType type) {
    this.type = type;
    return this;
  }

  public UserBuilder registrationTime(Date registrationTime) {
    this.registrationTime = registrationTime;
    return this;
  }

  public User build(UserStatus userStatus) {
    User user = new User();
    user.setUsername(this.username);
    user.setEmail(this.email);
    user.setPhoneNumber(this.phoneNumber);
    user.setPassword(this.password);
    user.setType(this.type);
    user.setRegistrationTime(this.registrationTime);
    user.addStatus(userStatus);
    return user;
  }
}

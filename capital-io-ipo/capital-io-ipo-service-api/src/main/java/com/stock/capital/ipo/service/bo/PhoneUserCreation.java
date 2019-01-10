package com.stock.capital.ipo.service.bo;

import static com.stock.capital.common.model.RegexPattern.REGEX_IDENTIFYING_CODE;
import static com.stock.capital.common.model.RegexPattern.REGEX_PHONE;
import static com.stock.capital.common.model.RegexPattern.REGEX_USER_PASSWORD;

import com.stock.capital.common.model.In;
import com.stock.capital.ipo.model.entity.User;
import com.stock.capital.ipo.model.entity.inner.UserType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * [PhoneUserCreation].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class PhoneUserCreation implements In<User> {

  private static final long serialVersionUID = -7114641012586282788L;

  @NotNull
  @Pattern(regexp = REGEX_PHONE)
  private String phoneNumber;

  @NotNull
  @Pattern(regexp = REGEX_USER_PASSWORD)
  private String password;

  @NotNull
  @Pattern(regexp = REGEX_IDENTIFYING_CODE)
  private String identifyingCode;

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getIdentifyingCode() {
    return identifyingCode;
  }

  public void setIdentifyingCode(String identifyingCode) {
    this.identifyingCode = identifyingCode;
  }

  @Override
  public User convert() {
    User userEntity = new User();
    userEntity.setUsername(this.getPhoneNumber());
    userEntity.setPassword(this.getPassword());
    userEntity.setPhoneNumber(this.getPhoneNumber());
    userEntity.setType(UserType.REGISTERED);
    return userEntity;
  }
}

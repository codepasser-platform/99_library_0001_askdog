package com.stock.capital.ipo.service.sample.bo;

import static com.stock.capital.common.model.RegexPattern.REGEX_GENERAL_NAME;
import static com.stock.capital.common.model.RegexPattern.REGEX_IDENTIFYING_CODE;
import static com.stock.capital.common.model.RegexPattern.REGEX_PHONE;
import static com.stock.capital.common.model.RegexPattern.REGEX_USER_NAME;
import static com.stock.capital.common.model.RegexPattern.REGEX_USER_PASSWORD;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * [SampleBo].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class SampleBo implements Serializable {

  private static final long serialVersionUID = 7559325840106564074L;

  @NotNull
  @Pattern(regexp = REGEX_USER_NAME)
  private String username;

  @NotNull
  @Pattern(regexp = REGEX_GENERAL_NAME)
  private String nickname;

  @NotNull
  @Pattern(regexp = REGEX_PHONE)
  private String phoneNumber;

  @NotNull
  @Pattern(regexp = REGEX_USER_PASSWORD)
  private String password;

  @NotNull
  @Pattern(regexp = REGEX_IDENTIFYING_CODE)
  private String identifyingCode;

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
}

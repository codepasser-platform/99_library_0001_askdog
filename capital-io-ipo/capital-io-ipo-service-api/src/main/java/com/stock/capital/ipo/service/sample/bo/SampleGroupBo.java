package com.stock.capital.ipo.service.sample.bo;

import static com.stock.capital.common.model.RegexPattern.REGEX_FAX;
import static com.stock.capital.common.model.RegexPattern.REGEX_GENERAL_NAME;
import static com.stock.capital.common.model.RegexPattern.REGEX_IDENTIFYING_CODE;
import static com.stock.capital.common.model.RegexPattern.REGEX_ID_CARD;
import static com.stock.capital.common.model.RegexPattern.REGEX_MAIL;
import static com.stock.capital.common.model.RegexPattern.REGEX_PHONE;
import static com.stock.capital.common.model.RegexPattern.REGEX_POST_CODE;
import static com.stock.capital.common.model.RegexPattern.REGEX_USER_NAME;
import static com.stock.capital.common.model.RegexPattern.REGEX_USER_PASSWORD;

import com.stock.capital.common.model.validation.Group;
import com.stock.capital.ipo.model.entity.inner.UserType;
import com.stock.capital.ipo.model.validation.UserIdentifier;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * [SampleGroupBo].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@UserIdentifier(
    groups = Group.Create.class,
    phoneNumberMethod = "getPhone",
    emailMethod = "getEmail")
public class SampleGroupBo implements Serializable {

  private static final long serialVersionUID = -3308685474130016053L;

  @NotNull(groups = Group.Create.class)
  private UserType type;

  @NotNull(groups = Group.Create.class)
  @Pattern(regexp = REGEX_USER_NAME)
  private String username;

  @NotNull(groups = Group.Create.class)
  @Pattern(regexp = REGEX_USER_PASSWORD)
  private String password;

  @Pattern(regexp = REGEX_PHONE)
  private String phone;

  @Pattern(regexp = REGEX_MAIL)
  private String email;

  @Pattern(regexp = REGEX_GENERAL_NAME)
  private String nickname;

  @Pattern(regexp = REGEX_IDENTIFYING_CODE)
  private String identifyingCode;

  @Pattern(regexp = REGEX_FAX)
  private String fax;

  @Pattern(regexp = REGEX_POST_CODE)
  private String postCode;

  @Pattern(regexp = REGEX_ID_CARD)
  private String idCard;

  public UserType getType() {
    return type;
  }

  public void setType(UserType type) {
    this.type = type;
  }

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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getIdentifyingCode() {
    return identifyingCode;
  }

  public void setIdentifyingCode(String identifyingCode) {
    this.identifyingCode = identifyingCode;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }
}

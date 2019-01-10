package com.stock.capital.ipo.service.bo;

import static com.stock.capital.common.model.RegexPattern.REGEX_GENERAL_NAME;

import com.stock.capital.common.business.category.Gender;
import com.stock.capital.common.business.info.Address;
import com.stock.capital.common.model.In;
import com.stock.capital.ipo.model.data.UserAttribute;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.springframework.beans.BeanUtils;

/**
 * [PhoneUserCreation].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class UserAttributeCreation implements In<UserAttribute> {

  private static final long serialVersionUID = 5088205165082583727L;

  @NotNull private Long userId;

  @Pattern(regexp = REGEX_GENERAL_NAME)
  private String realName;

  private Gender gender;

  private Address address;

  private String occupation;

  private String school;

  private String major;

  private String signature;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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

  @Override
  public UserAttribute convert() {
    UserAttribute userAttribute = new UserAttribute();
    BeanUtils.copyProperties(this, userAttribute);
    return userAttribute;
  }
}

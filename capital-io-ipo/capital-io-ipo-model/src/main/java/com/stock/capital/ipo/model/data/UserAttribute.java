package com.stock.capital.ipo.model.data;

import com.stock.capital.common.business.category.Gender;
import com.stock.capital.common.business.info.Address;
import com.stock.capital.common.model.data.BaseData;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * [UserAttribute].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@Document(collection = "sys_user_attribute ")
public class UserAttribute extends BaseData {

  private static final long serialVersionUID = -1520236753298111983L;

  private Long userId;

  private String realName;

  private Gender gender;

  private Address address;

  private String occupation;

  private String school;

  private String major;

  private String signature;

  private boolean isUpdateUserBasicInfo = true;

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

  public boolean isUpdateUserBasicInfo() {
    return isUpdateUserBasicInfo;
  }

  public void setUpdateUserBasicInfo(boolean updateUserBasicInfo) {
    isUpdateUserBasicInfo = updateUserBasicInfo;
  }
}

package com.askdog.web.api.vo.profile;

import com.askdog.common.In;
import com.askdog.model.data.UserAttribute.Address;
import com.askdog.model.data.UserAttribute.Gender;
import com.askdog.service.bo.UserDetail;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

import static com.askdog.common.RegexPattern.REGEX_PHONE;

public class PostedUserDetail implements In<UserDetail> {

    @Pattern(regexp = REGEX_PHONE)
    private String phone;
    private Gender gender;
    @Valid
    private Address address;
    @Pattern(regexp = ".{0,37}")
    private String occupation;
    @Pattern(regexp = ".{0,37}")
    private String major;
    @Pattern(regexp = ".{0,37}")
    private String school;

    @Size(max = 5)
    private Set<String> interestTag;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Set<String> getInterestTag() {
        return interestTag;
    }

    public void setInterestTag(Set<String> interestTag) {
        this.interestTag = interestTag;
    }

    @Override
    public UserDetail convert() {
        UserDetail detail = new UserDetail();
        detail.setPhone(this.phone);
        detail.setGender(this.gender);
        detail.setAddress(this.address);
        detail.setOccupation(this.occupation);
        detail.setSchool(this.school);
        detail.setMajor(this.major);
        detail.setInterestTag(this.interestTag);
        return detail;
    }

}

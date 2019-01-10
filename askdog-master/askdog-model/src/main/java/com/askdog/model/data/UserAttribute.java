package com.askdog.model.data;

import java.io.Serializable;
import java.util.Set;

public class UserAttribute extends Base implements Serializable {

    private static final long serialVersionUID = -3163187605126868852L;

    private String userId;
    private Gender gender;
    private String phone;
    private Address address;
    private String occupation;
    private String school;
    private String major;
    private Set<String> interestTag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Set<String> getInterestTag() {
        return interestTag;
    }

    public void setInterestTag(Set<String> interestTag) {
        this.interestTag = interestTag;
    }

    public enum Gender {
        MALE, FEMALE
    }

    public static class Address implements Serializable {

        private static final long serialVersionUID = -674513753585233476L;

        // TODO: 16-6-11 not in user'profile basic info
        private String province;
        private String city;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}

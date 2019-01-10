package com.askdog.web.api.vo.profile;

import com.askdog.common.Out;
import com.askdog.model.data.UserAttribute.Address;
import com.askdog.service.bo.UserDetail;

import java.util.Set;

import static com.askdog.model.data.UserAttribute.Gender;

public class PersonalInfo implements Out<PersonalInfo, UserDetail> {

    private String id;
    private String name;
    private String mail;
    private String avatar;
    private long points;
    private long exp;
    private Gender gender;
    private String phone;
    private Address address;
    private String occupation;
    private String major;
    private String school;
    private Set<String> interestTag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
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
    public PersonalInfo from(UserDetail entity) {
        this.id = entity.getUuid();
        this.name = entity.getName();
        this.mail = entity.getMail();
        this.avatar = entity.getAvatar();
        this.points = entity.getPoints();
        this.exp = entity.getExp();
        this.gender = entity.getGender();
        this.phone = entity.getPhone();
        this.address = entity.getAddress();
        this.occupation = entity.getOccupation();
        this.major = entity.getMajor();
        this.school = entity.getSchool();
        this.interestTag = entity.getInterestTag();
        return this;
    }
}

package com.askdog.service.bo;

import com.askdog.common.In;
import com.askdog.common.Out;
import com.askdog.model.data.UserAttribute;
import com.askdog.model.data.UserAttribute.Gender;
import com.askdog.model.entity.User;
import com.askdog.model.entity.inner.user.UserType;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Set;

import static com.askdog.model.data.UserAttribute.Address;

public class UserDetail implements Out<UserDetail, User>, In<UserAttribute>, Serializable {

    private static final long serialVersionUID = 2654072793251453099L;

    private String uuid;
    private UserType type;
    private String name;
    private String mail;
    private String avatar;
    private long points;
    private long exp;
    private String phone;

    private Gender gender;
    private Address address;
    private String occupation;
    private String major;
    private String school;
    private Set<String> interestTag;

    public String getUuid() {
        return uuid;
    }

    public UserDetail setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
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
    public UserDetail from(User entity) {
        this.uuid = entity.getUuid();
        this.type = entity.getType();
        this.name = entity.getName();
        this.mail = entity.getEmail();
        this.avatar = entity.getAvatarUrl();
        this.points = entity.getPoints();
        this.exp = entity.getExp();
        return this;
    }

    @Override
    public UserAttribute convert() {
        UserAttribute attribute = new UserAttribute();
        attribute.setUserId(this.uuid);
        attribute.setGender(this.gender);
        attribute.setPhone(this.phone);
        attribute.setAddress(this.address);
        attribute.setOccupation(this.occupation);
        attribute.setSchool(this.school);
        attribute.setMajor(this.major);
        attribute.setInterestTag(this.interestTag);
        return attribute;
    }

    public UserDetail setAttributes(@Nonnull UserAttribute attributes) {
        this.setGender(attributes.getGender());
        this.setPhone(attributes.getPhone());
        this.setAddress(attributes.getAddress());
        this.setOccupation(attributes.getOccupation());
        this.setSchool(attributes.getSchool());
        this.setMajor(attributes.getMajor());
        this.setInterestTag(attributes.getInterestTag());
        return this;
    }
}

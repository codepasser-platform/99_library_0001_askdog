package com.askdog.service.bo;

import com.askdog.service.bo.common.Location;
import com.askdog.service.bo.store.ContactsUserDetail;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public class StoreDetail implements Serializable {

    private static final long serialVersionUID = 251371839698022656L;

    @JsonFormat(shape = STRING)
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private Location location;
    private String type;
    private Float cpc;
    private String businessHours;
    private Date creationTime;
    private UserDetail owner;
    private AgentDetail agent;
    private String coverImage;
    private boolean deleted;
    private boolean mine;
    private boolean deletable;
    private boolean editable;
    private ContactsUserDetail contactsUserDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getCpc() {
        return cpc;
    }

    public void setCpc(Float cpc) {
        this.cpc = cpc;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public UserDetail getOwner() {
        return owner;
    }

    public void setOwner(UserDetail owner) {
        this.owner = owner;
    }

    public AgentDetail getAgent() {
        return agent;
    }

    public void setAgent(AgentDetail agent) {
        this.agent = agent;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public ContactsUserDetail getContactsUserDetail() {
        return contactsUserDetail;
    }

    public void setContactsUserDetail(ContactsUserDetail contactsUserDetail) {
        this.contactsUserDetail = contactsUserDetail;
    }
}

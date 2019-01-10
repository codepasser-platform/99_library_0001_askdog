package com.askdog.service.bo.product;

import com.askdog.model.entity.Product.ProductTags;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class ProductCreation implements Serializable {

    private static final long serialVersionUID = -1926019406283434998L;

    @NotNull
    @Size(min = 1, max = 15)
    private String name;

    @NotNull
    @Size(max = 50000)
    private String description;

    private Long videoId;

    @NotNull
    private Long storeId;

    private Set<Long> coupons = newHashSet();

    private Long coverId;

    private List<Long> pictures;

    private EnumSet<ProductTags> tags;

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

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Set<Long> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<Long> coupons) {
        this.coupons = coupons;
    }

    public Long getCoverId() {
        return coverId;
    }

    public void setCoverId(Long coverId) {
        this.coverId = coverId;
    }

    public List<Long> getPictures() {
        return pictures;
    }

    public void setPictures(List<Long> pictures) {
        this.pictures = pictures;
    }

    public EnumSet<ProductTags> getTags() {
        return tags;
    }

    public void setTags(EnumSet<ProductTags> tags) {
        this.tags = tags;
    }
}

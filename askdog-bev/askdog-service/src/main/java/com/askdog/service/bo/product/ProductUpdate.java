package com.askdog.service.bo.product;

import com.askdog.model.entity.Product.ProductTags;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class ProductUpdate implements Serializable {

    private static final long serialVersionUID = -764026897867619952L;

    private Long id;

    @NotNull
    @Size(min = 1, max = 15)
    private String name;

    @NotNull
    @Size(max = 50000)
    private String description;

    private Long videoId;

    private Set<Long> coupons = newHashSet();

    private Long coverId;

    private List<Long> pictures;

    private EnumSet<ProductTags> tags;

    public Long getId() {
        return id;
    }

    public ProductUpdate setId(Long id) {
        this.id = id;
        return this;
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

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
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

package com.askdog.store.model.data.inner.area;

import com.askdog.store.model.data.Area;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaProvince extends Area implements AreaTree<Area, AreaCity> {

    private String provinceId;
    private String provinceName;
    private String parentId;
    private int level = AreaLevel.PROVINCE.level();
    private List<AreaCity> children;

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Nonnull
    @Override
    public String getAreaId() {
        return this.getProvinceId();
    }

    @Nonnull
    @Override
    public String getAreaName() {
        return this.getProvinceName();
    }

    @Nullable
    @Override
    public String getParentId() {
        return null;
    }

    @Nullable
    @Override
    public Area getParent() {
        return null;
    }

    @Override
    public void setParent(@Nonnull Area parent) {
    }

    @Nullable
    @Override
    public List<AreaCity> getChildren() {
        return children;
    }

    @Override
    public void setChildren(@Nonnull List<AreaCity> children) {
        this.children = children;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}

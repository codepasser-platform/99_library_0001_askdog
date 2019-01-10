package com.askdog.store.model.data.inner.area;

import com.askdog.store.model.data.Area;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaCity extends Area implements AreaTree<AreaProvince, AreaCounty> {

    private String provinceId;
    private String provinceName;
    private String cityId;
    private String cityName;
    private String parentId;
    private int level = AreaLevel.CITY.level();
    private AreaProvince parent;
    private List<AreaCounty> children;

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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Nonnull
    @Override
    public String getAreaId() {
        return this.getCityId();
    }

    @Nonnull
    @Override
    public String getAreaName() {
        return this.getCityName();
    }

    @Nullable
    @Override
    public String getParentId() {
        return this.getProvinceId();
    }

    @Nullable
    @Override
    public AreaProvince getParent() {
        return parent;
    }

    @Override
    public void setParent(@Nonnull AreaProvince parent) {
        this.parent = parent;
    }

    @Nullable
    @Override
    public List<AreaCounty> getChildren() {
        return children;
    }

    @Override
    public void setChildren(@Nonnull List<AreaCounty> children) {
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

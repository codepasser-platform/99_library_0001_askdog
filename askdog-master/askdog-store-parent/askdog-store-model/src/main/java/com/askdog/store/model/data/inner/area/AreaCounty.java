package com.askdog.store.model.data.inner.area;

import com.askdog.store.model.data.Area;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaCounty extends Area implements AreaTree<AreaCity, AreaTown> {

    private String provinceId;
    private String provinceName;
    private String cityId;
    private String cityName;
    private String countyId;
    private String countyName;
    private String parentId;
    private int level = AreaLevel.COUNTY.level();
    private AreaCity parent;
    private List<AreaTown> children;

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

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    @Nonnull
    @Override
    public String getAreaId() {
        return this.getCountyId();
    }

    @Nonnull
    @Override
    public String getAreaName() {
        return this.getCountyName();
    }

    @Nullable
    @Override
    public String getParentId() {
        return this.getCityId();
    }

    @Nullable
    @Override
    public AreaCity getParent() {
        return parent;
    }

    @Override
    public void setParent(@Nonnull AreaCity parent) {
        this.parent = parent;
    }

    @Nullable
    @Override
    public List<AreaTown> getChildren() {
        return children;
    }

    @Override
    public void setChildren(@Nonnull List<AreaTown> children) {
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

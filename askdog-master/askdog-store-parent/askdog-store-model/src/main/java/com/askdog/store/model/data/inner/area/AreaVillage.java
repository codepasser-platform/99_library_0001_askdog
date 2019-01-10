package com.askdog.store.model.data.inner.area;

import com.askdog.store.model.data.Area;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class AreaVillage extends Area implements AreaTree<AreaTown, Area> {

    private String provinceId;
    private String provinceName;
    private String cityId;
    private String cityName;
    private String countyId;

    private String countyName;
    private String townId;
    private String townName;
    private String villageId;
    private String villageName;
    private String parentId;
    private int level = AreaLevel.VILLAGE.level();
    private AreaTown parent;

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

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    @Nonnull
    @Override
    public String getAreaId() {
        return this.getVillageId();
    }

    @Nonnull
    @Override
    public String getAreaName() {
        return this.getVillageName();
    }

    @Nullable
    @Override
    public String getParentId() {
        return this.getTownId();
    }

    @Nullable
    @Override
    public AreaTown getParent() {
        return parent;
    }

    @Override
    public void setParent(@Nonnull AreaTown parent) {
        this.parent = parent;
    }

    @Nullable
    @Override
    public List<Area> getChildren() {
        return null;
    }

    @Override
    public void setChildren(@Nonnull List<Area> children) {
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
}

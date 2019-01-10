package com.askdog.store.model.repository.extend;

import com.askdog.store.model.data.Area;
import com.askdog.store.model.data.inner.area.*;

import java.util.Collection;
import java.util.List;

public interface ExtendedAreaTreeRepository {

    void insertAll(Collection<Area> areas);

    List<AreaProvince> findAllProvince();

    List<AreaCity> findChildrenByProvinceId(String provinceId);

    List<AreaCounty> findChildrenByCityId(String cityId);

    List<AreaTown> findChildrenByCountyId(String countyId);

    List<AreaVillage> findChildrenByTownId(String townId);
}

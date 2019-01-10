package com.askdog.store.service.impl;

import com.askdog.store.model.data.Area;
import com.askdog.store.model.data.inner.area.AreaTree.AreaLevel;
import com.askdog.store.model.repository.mongo.AreaTreeRepository;
import com.askdog.store.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private AreaTreeRepository areaTreeRepository;

    @Nonnull
    @Override
    public <T extends Area> List<T> areas(AreaLevel level, @Nullable String parentId) {
        switch (level) {
            case HEAD:
                return (List<T>) areaTreeRepository.findAllProvince();
            case PROVINCE:
                return (List<T>) areaTreeRepository.findAllProvince();
            case CITY:
                return (List<T>) areaTreeRepository.findChildrenByProvinceId(parentId);
            case COUNTY:
                return (List<T>) areaTreeRepository.findChildrenByCityId(parentId);
            case TOWN:
                return (List<T>) areaTreeRepository.findChildrenByCountyId(parentId);
            case VILLAGE:
                return (List<T>) areaTreeRepository.findChildrenByTownId(parentId);
            default:
                return (List<T>) areaTreeRepository.findAllProvince();
        }
    }

}

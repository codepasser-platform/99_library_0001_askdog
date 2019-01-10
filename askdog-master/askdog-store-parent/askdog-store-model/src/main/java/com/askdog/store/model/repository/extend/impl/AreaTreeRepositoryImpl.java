package com.askdog.store.model.repository.extend.impl;

import com.askdog.store.model.data.Area;
import com.askdog.store.model.data.inner.area.*;
import com.askdog.store.model.repository.extend.ExtendedAreaTreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.query.Query.query;

public class AreaTreeRepositoryImpl implements ExtendedAreaTreeRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    private void init() {
        mongoTemplate.indexOps(AreaProvince.class).ensureIndex(new Index("order", DESC));
    }

    @Override
    public void insertAll(Collection<Area> areas) {
        this.mongoTemplate.insertAll(areas);
    }

    @Override
    public List<AreaProvince> findAllProvince() {
        return this.mongoTemplate.find(
                query(
                        Criteria.where("display").is(true)
                ).with(new Sort(new Sort.Order(ASC, "order")))
                , AreaProvince.class);
    }

    @Override
    public List<AreaCity> findChildrenByProvinceId(String provinceId) {
        return this.mongoTemplate.find(
                query(
                        Criteria.where("provinceId").is(provinceId)
                ),
                AreaCity.class
        );
    }

    @Override
    public List<AreaCounty> findChildrenByCityId(String cityId) {
        return this.mongoTemplate.find(
                query(
                        Criteria.where("cityId").is(cityId)
                ),
                AreaCounty.class
        );
    }

    @Override
    public List<AreaTown> findChildrenByCountyId(String countyId) {
        return this.mongoTemplate.find(
                query(
                        Criteria.where("countyId").is(countyId)
                ),
                AreaTown.class
        );
    }

    public List<AreaVillage> findChildrenByTownId(String townId) {
        return this.mongoTemplate.find(
                query(
                        Criteria.where("townId").is(townId)
                ),
                AreaVillage.class
        );
    }

}

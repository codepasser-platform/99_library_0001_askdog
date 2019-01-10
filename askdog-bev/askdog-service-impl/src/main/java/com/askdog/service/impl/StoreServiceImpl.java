package com.askdog.service.impl;

import com.askdog.common.utils.Json;
import com.askdog.dao.repository.AgentRepository;
import com.askdog.dao.repository.StoreRepository;
import com.askdog.dao.repository.UserRepository;
import com.askdog.dao.repository.mongo.StoreAttributeRepository;
import com.askdog.model.data.StoreAttribute;
import com.askdog.model.data.inner.location.LocationDescription;
import com.askdog.model.entity.Agent;
import com.askdog.model.entity.Product;
import com.askdog.model.entity.Store;
import com.askdog.model.entity.User;
import com.askdog.service.AgentService;
import com.askdog.service.ProductService;
import com.askdog.service.StoreService;
import com.askdog.service.UserService;
import com.askdog.service.bo.BasicUser;
import com.askdog.service.bo.admin.dashboard.StoreStatistics;
import com.askdog.service.bo.common.PagedData;
import com.askdog.service.bo.store.AmendedStore;
import com.askdog.service.bo.store.PureStore;
import com.askdog.service.bo.store.StoreBasic;
import com.askdog.service.bo.store.StoreDetail;
import com.askdog.service.exception.ForbiddenException;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.impl.cell.StoreCell;
import com.askdog.service.impl.cell.UserCell;
import com.askdog.service.impl.location.LocationConfiguration;
import com.askdog.service.impl.location.tencent.PlaceLocationDescription;
import com.askdog.service.impl.storage.StorageRecorder;
import com.askdog.service.location.LocationAgent;
import com.askdog.service.location.Provider;
import com.askdog.service.location.ResponseBody;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.askdog.common.utils.Conditions.checkState;
import static com.askdog.model.common.State.DELETED;
import static com.askdog.model.common.State.OK;
import static com.askdog.model.data.inner.location.LocationProvider.TENCENT_MAP;
import static com.askdog.model.entity.builder.StoreBuilder.storeBuilder;
import static com.askdog.model.security.Authority.Role.STORE_ADMIN;
import static com.askdog.service.bo.common.PagedDataUtils.rePage;
import static com.askdog.service.exception.NotFoundException.Error.STORE;
import static com.google.common.collect.Sets.newHashSet;


@RestController
public class StoreServiceImpl implements StoreService {

    @Autowired private StoreRepository storeRepository;
    @Autowired private AgentRepository agentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UserService userService;
    @Autowired private AgentService agentService;
    @Autowired private StoreCell storeCell;
    @Autowired private UserCell userCell;
    @Autowired private StorageRecorder storageRecorder;
    @Autowired private ProductService productService;
    @Autowired private StoreAttributeRepository sar;
    @Provider(TENCENT_MAP)
    @Autowired private LocationAgent locationAgent;
    @Autowired private LocationConfiguration locationConfiguration;

    @Nonnull
    @Override
    public com.askdog.service.bo.StoreDetail findDetail(@PathVariable("storeId") Long storeId,
                                                        @RequestParam(name = "enableDeleted", defaultValue = "false") boolean enableDeleted) {
        com.askdog.service.bo.StoreDetail storeDetail = storeCell.findDetail(storeId);
        checkState(enableDeleted || !storeDetail.isDeleted(), () -> new NotFoundException(STORE));
        storeDetail.setOwner(userService.findDetail(storeDetail.getOwner().getId()));
        if (null != storeDetail.getAgent()) {
            storeDetail.setAgent(agentService.findDetail(storeDetail.getAgent().getId(), enableDeleted));
        }
        return storeDetail;
    }

    @Nonnull
    @Override
    public com.askdog.service.bo.StoreDetail findDetailWithState(@RequestParam("userId") long userId,
                                                                 @PathVariable("storeId") long storeId,
                                                                 @RequestParam(name = "enableDeleted", defaultValue = "false") boolean enableDeleted) {
        com.askdog.service.bo.StoreDetail storeDetail = findDetail(storeId, enableDeleted);
        storeCell.fillInState(userId, storeDetail);
        return storeDetail;
    }

    @Nonnull
    @Override
    public StoreDetail findPageDetail(@RequestParam(value = "userId", required = false) Long userId, @PathVariable("storeId") long storeId) {
        Long productId = storeCell.getSpecialProductId(storeId, Product.ProductTags.SPECIAL);
        StoreDetail storeDetail = new StoreDetail().from(findDetail(storeId, false));
        if (productId != null) {
            storeDetail.setSpecialProduct(productService.getPageDetail(userId, productId));
        }
        return storeDetail;
    }

    @Nonnull
    @Override
    public StoreDetail findPageDetailWithState(@RequestParam("userId") long userId, @PathVariable("storeId") long storeId) {
        return new StoreDetail().from(findDetailWithState(userId, storeId, false));
    }

    @Nonnull
    @Override
    public PagedData<StoreBasic> getStores(@RequestParam("ip") @Nonnull String ip,
                                           @RequestParam(name = "lat", required = false) Double lat,
                                           @RequestParam(name = "lng", required = false) Double lng,
                                           @RequestBody Pageable pageable) {

        if (lat == null || lng == null) {
            ResponseBody responseBody = locationAgent.analysisAddress(ip);
            if (responseBody.isSuccess()) {
                PlaceLocationDescription locationDescription = Json.readValue(responseBody.getData(), PlaceLocationDescription.class);
                Assert.notNull(locationDescription);
                LocationDescription.Location location = locationDescription.getLocation();
                Assert.notNull(location);
                lat = location.getLat();
                lng = location.getLng();
            }
        }

        if (lat != null && lng != null) {
            Point point = new Point(lng, lat);
            Distance distance = new Distance(locationConfiguration.getNearbyDistance(), Metrics.KILOMETERS);
            Page<StoreAttribute> storeAttributes = sar.findByGeoNearAndStateNotIn(point, distance, newHashSet(DELETED), pageable);

            if (storeAttributes.hasContent()) {
                return rePage(storeAttributes, pageable, attribute -> new StoreBasic().from(findPageDetail(null, attribute.getStoreId())));
            }
        }

        Page<Store> stores = storeRepository.findAllByStateNotIn(newHashSet(DELETED), pageable);
        return rePage(stores, pageable, store -> new StoreBasic().from(findPageDetail(null, store.getId())));
    }

    @Nonnull
    @Override
    public PagedData<StoreDetail> getStoreByRole(@RequestParam("userId") long userId,
                                                 @RequestBody Pageable pageable) {
        Agent agent = agentRepository.findByOwner_IdAndStateNotIn(userId, newHashSet(DELETED)).orElse(null);
        Page<Store> stores;
        if (storeCell.isAdmin(userId)) {
            stores = storeRepository.findAllByStateNotIn(newHashSet(DELETED), pageable);
        } else {
            stores = storeRepository.findByAgentAndStateNotIn(agent, newHashSet(DELETED), pageable);
        }
        return rePage(stores, pageable, store -> new StoreDetail().from(storeCell.findDetail(store.getId())));
    }

    @Nonnull
    @Override
    public List<Long> findOwnedStores(@PathVariable("userId") long userId) {
        return storeCell.findOwnedStores(userId);
    }

    @Nonnull
    @Override
    public StoreDetail create(@PathVariable("userId") long userId,
                              @Nonnull @Valid @RequestBody PureStore pureStore) {
        User bindUser = userCell.findExists(pureStore.getUserId());
        Agent agent = agentRepository.findByOwner_IdAndStateNotIn(userId, newHashSet(DELETED)).orElse(null);

        if (pureStore.getCoverImageLinkId() != null) {
            storageRecorder.assertValid(pureStore.getCoverImageLinkId());
        }

        Store store = storeBuilder()
                .name(pureStore.getName())
                .description(pureStore.getDescription())
                .address(pureStore.getAddress())
                .phone(pureStore.getPhone())
                .cover(pureStore.getCoverImageLinkId())
                .owner(bindUser)
                .agent(agent)
                .contactsName(pureStore.getPureContactsUser().getName())
                .contactsPhone(pureStore.getPureContactsUser().getPhone())
                .build();

        StoreAttribute storeAttribute = new StoreAttribute();
        storeAttribute.setState(OK);
        storeAttribute.setCreationTime(store.getCreationTime());
        storeAttribute.setStoreId(store.getId());

        PureStore.Location location = pureStore.getLocation();
        if (location != null) {
            GeoJsonPoint geo = new GeoJsonPoint(new Point(location.getLng(), location.getLat()));
            storeAttribute.setGeo(geo);
        }

        storeAttribute.setType(pureStore.getType());
        storeAttribute.setCpc(pureStore.getCpc());
        storeAttribute.setBusinessHours(pureStore.getBusinessHours());

        sar.save(storeAttribute);

        Store savedStore = storeRepository.save(store);
        storeCell.refreshOwnedStoresCache(bindUser.getId());
        return findPageDetail(userId, savedStore.getId());
    }

    @Nonnull
    @Override
    public StoreDetail update(@PathVariable("userId") long userId,
                              @PathVariable("storeId") long storeId,
                              @Nonnull @Valid @RequestBody AmendedStore amendedStore) {
        com.askdog.service.bo.StoreDetail storeDetail = findDetailWithState(userId, storeId, false);
        checkState(storeDetail.isEditable(), () -> new ForbiddenException(ForbiddenException.Error.UPDATE_STORE));

        if (amendedStore.getCoverImageLinkId() != null) {
            storageRecorder.assertValid(amendedStore.getCoverImageLinkId());
        }

        Store store = storeCell.findExist(storeId);

        if (!Strings.isNullOrEmpty(amendedStore.getName())) {
            store.setName(amendedStore.getName());
        }
        if (!Strings.isNullOrEmpty(amendedStore.getAddress())) {
            store.setAddress(amendedStore.getAddress());
        }
        if (!Strings.isNullOrEmpty(amendedStore.getPhone())) {
            store.setPhone(amendedStore.getPhone());
        }
        if (!Strings.isNullOrEmpty(amendedStore.getDescription())) {
            store.setDescription(amendedStore.getDescription());
        }

        if (amendedStore.getCoverImageLinkId() != null) {
            storageRecorder.assertValid(amendedStore.getCoverImageLinkId());
            store.setCover(amendedStore.getCoverImageLinkId());
        }

        store.setContactsName(amendedStore.getPureContactsUser().getName());
        store.setContactsPhone(amendedStore.getPureContactsUser().getPhone());

        StoreAttribute storeAttribute = sar.findByStoreId(storeId);

        if (storeAttribute == null) {
            storeAttribute = new StoreAttribute();
            storeAttribute.setStoreId(storeId);
        }

        PureStore.Location location = amendedStore.getLocation();
        if (location != null) {
            GeoJsonPoint geo = new GeoJsonPoint(new Point(location.getLng(), location.getLat()));
            storeAttribute.setGeo(geo);
        }

        storeAttribute.setType(amendedStore.getType());
        storeAttribute.setCpc(amendedStore.getCpc());
        storeAttribute.setBusinessHours(amendedStore.getBusinessHours());

        sar.save(storeAttribute);

        storeRepository.save(store);
        storeCell.refreshBasicCache(storeId);

        return findPageDetail(userId, storeId);
    }

    @Override
    @Transactional
    public void delete(@PathVariable("userId") long userId,
                       @PathVariable("storeId") long storeId) {
        com.askdog.service.bo.StoreDetail storeDetail = findDetailWithState(userId, storeId, false);
        checkState(storeDetail.isDeletable(), () -> new ForbiddenException(ForbiddenException.Error.DELETE_STORE));
        Store store = storeCell.findExist(storeId);
        store.setState(DELETED);
        storeRepository.save(store);

        StoreAttribute storeAttribute = sar.findByStoreId(storeId);
        if (storeAttribute != null) {
            storeAttribute.setState(DELETED);
            sar.save(storeAttribute);
        }

        storeCell.refreshBasicCache(storeId);
        storeCell.refreshOwnedStoresCache(storeDetail.getOwner().getId());

        User user = userCell.findExists(store.getOwner().getId());
        if (null != user.getAuthorities()) {
            user.getAuthorities().remove(STORE_ADMIN);
        }
        userRepository.save(user);
    }

    @Override
    public StoreStatistics storeStatistic() {
        StoreStatistics storeStatistics = new StoreStatistics();
        storeStatistics.setTotalStoreCount(storeRepository.countRegisteredStore());
        storeStatistics.setStoreRegistrationTrend(storeRepository.storeRegistrationStatistics("day", "1 years"));
        return storeStatistics;
    }

    @Override
    public List<BasicUser> search(@PathVariable("key") String key) {
        List<User> users = userRepository.search(key);
        List<BasicUser> userBasics = new ArrayList<>();
        for (User user : users) {
            userBasics.add(userCell.findDetail(user.getId()).toBasic());
        }
        return userBasics;
    }

}

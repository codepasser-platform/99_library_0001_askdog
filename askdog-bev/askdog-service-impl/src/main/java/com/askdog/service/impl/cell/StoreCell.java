package com.askdog.service.impl.cell;

import com.askdog.dao.repository.ProductRepository;
import com.askdog.dao.repository.StoreRepository;
import com.askdog.dao.repository.mongo.StoreAttributeRepository;
import com.askdog.model.data.StoreAttribute;
import com.askdog.dao.repository.UserRepository;
import com.askdog.model.entity.Product;
import com.askdog.model.entity.Product.ProductTags;
import com.askdog.model.entity.Store;
import com.askdog.model.entity.User;
import com.askdog.model.common.State;
import com.askdog.model.security.Authority;
import com.askdog.service.bo.AgentDetail;
import com.askdog.service.bo.StoreDetail;
import com.askdog.service.bo.UserDetail;
import com.askdog.service.bo.common.Location;
import com.askdog.service.bo.store.ContactsUserDetail;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.impl.cache.annotation.store.StoreBasicCache;
import com.askdog.service.impl.cache.annotation.store.StoreBasicCacheRefresh;
import com.askdog.service.impl.cache.annotation.store.StoresOwnedCache;
import com.askdog.service.impl.cache.annotation.store.StoresOwnedCacheRefresh;
import com.askdog.service.impl.storage.StorageRecorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static com.askdog.model.common.State.DELETED;
import static com.askdog.service.exception.NotFoundException.Error.STORE;
import static com.askdog.service.exception.NotFoundException.Error.USER;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.stream.Collectors.toList;

@Component
@Transactional
public class StoreCell {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StorageRecorder storageRecorder;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserCell userCell;
    @Autowired
    private UserRepository userRepository;
    @Autowired private StoreAttributeRepository storeAttributeRepository;

    @Nonnull
    @StoreBasicCache
    public StoreDetail findDetail(long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new NotFoundException(STORE));
        StoreAttribute storeAttribute = storeAttributeRepository.findByStoreId(storeId);

        StoreDetail storeDetail = new StoreDetail();
        storeDetail.setId(store.getId());
        storeDetail.setName(store.getName());
        storeDetail.setDescription(store.getDescription());
        storeDetail.setAddress(store.getAddress());
        storeDetail.setPhone(store.getPhone());
        storeDetail.setCreationTime(store.getCreationTime());
        storeDetail.setDeleted(store.getState() == State.DELETED);

        ContactsUserDetail contactsUserDetail = new ContactsUserDetail();
        contactsUserDetail.setName(store.getContactsName());
        contactsUserDetail.setPhone(store.getContactsPhone());
        storeDetail.setContactsUserDetail(contactsUserDetail);

        UserDetail userDetail = userCell.findDetail(store.getOwner().getId());
        storeDetail.setOwner(userDetail);

        if (null != store.getAgent()) {
            AgentDetail agentDetail = new AgentDetail();
            agentDetail.setId(store.getAgent().getId());
            storeDetail.setAgent(agentDetail);
        }

        Long coverImageId = store.getCover();
        if (null != coverImageId) {
            storeDetail.setCoverImage(storageRecorder.getResource(coverImageId).getDescription().getResourceUrl());
        }

        if (storeAttribute != null) {
            if (storeAttribute.getGeo() != null) {
                Location location = new Location();
                location.setLat(storeAttribute.getGeo().getY());
                location.setLng(storeAttribute.getGeo().getX());
                storeDetail.setLocation(location);
            }

            storeDetail.setType(storeAttribute.getType());
            storeDetail.setCpc(storeAttribute.getCpc());
            storeDetail.setBusinessHours(storeAttribute.getBusinessHours());
        }

        return storeDetail;
    }

    @Nonnull
    public StoreDetail fillInState(Long userId, StoreDetail storeDetail) {
        storeDetail.setMine(isMine(userId, storeDetail));
        storeDetail.setDeletable(isDeletable(userId, storeDetail));
        storeDetail.setEditable(isUpdatable(userId, storeDetail));
        return storeDetail;
    }

    public boolean isMine(long userId, @Nonnull StoreDetail storeDetail) {
        return storeDetail.getOwner().getId().equals(userId);
        //|| (null != storeDetail.getAgent() && storeDetail.getAgent().getOwner().getId().equals(userId));
    }

    public boolean isDeletable(long userId, @Nonnull StoreDetail storeDetail) {
        //return !storeDetail.isDeleted();
        return (storeDetail.getOwner().getId().equals(userId) || ((null != storeDetail.getAgent()) && (storeDetail.getAgent().getOwner().getId().equals(userId)))) || isAdmin(userId);
    }

    public boolean isUpdatable(long userId, @Nonnull StoreDetail storeDetail) {
        //return !storeDetail.isDeleted();
        return (storeDetail.getOwner().getId().equals(userId) || ((null != storeDetail.getAgent()) && (storeDetail.getAgent().getOwner().getId().equals(userId)))) || isAdmin(userId);
    }

    public Store findExist(Long storeId) {
        return storeRepository.findByIdAndStateNotIn(storeId, newHashSet(DELETED)).orElseThrow(() -> new NotFoundException(STORE));
    }

    @StoreBasicCacheRefresh
    public StoreDetail refreshBasicCache(long storeId) {
        return findDetail(storeId);
    }

    @Nonnull
    @StoresOwnedCache
    public List<Long> findOwnedStores(long userId) {
        List<Store> stores = storeRepository.findByOwner_IdAndStateNotIn(userId, newHashSet(DELETED));
        return stores.stream().map(Store::getId).collect(toList());
    }

    @StoresOwnedCacheRefresh
    public List<Long> refreshOwnedStoresCache(long userId) {
        List<Store> stores = storeRepository.findByOwner_IdAndStateNotIn(userId, newHashSet(DELETED));
        return stores.stream().map(Store::getId).collect(toList());
    }

    public Long getSpecialProductId(Long storeId, ProductTags productTags) {
        List<Product> products = productRepository.findByStore_IdAndStateNotIn(storeId, newHashSet(DELETED));
        if (products != null) {
            Optional<Long> productId = products.stream()
                    .sorted(Comparator.comparing(Product::getCreationTime).reversed())
                    .filter(each -> {
                        EnumSet<ProductTags> tagSet = each.getTags();
                        return tagSet != null && tagSet.contains(productTags);
                    })
                    .map(Product::getId)
                    .findFirst();
            return productId.isPresent() ? productId.get() : null;
        }
        return null;
    }

    public boolean isAdmin(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER));
        return user.getAuthorities() != null && user.getAuthorities().contains(Authority.Role.ADMIN);
    }
}

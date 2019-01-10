package com.askdog.service.impl;

import com.askdog.common.utils.Conditions;
import com.askdog.dao.repository.CouponRepository;
import com.askdog.dao.repository.ProductRepository;
import com.askdog.dao.repository.StoreRepository;
import com.askdog.dao.repository.UserCouponRepository;
import com.askdog.model.entity.*;
import com.askdog.model.entity.inner.coupon.CouponState;
import com.askdog.model.entity.inner.coupon.CouponType;
import com.askdog.model.security.Authority;
import com.askdog.service.CouponService;
import com.askdog.service.StoreService;
import com.askdog.service.UserService;
import com.askdog.service.bo.*;
import com.askdog.service.bo.coupon.CouponDetail;
import com.askdog.service.bo.coupon.CouponPageDetail;
import com.askdog.service.bo.usercoupon.UserCouponConsumeDetail;
import com.askdog.service.bo.common.PagedData;
import com.askdog.service.bo.coupon.CouponDetailBasic;
import com.askdog.service.bo.coupon.PureCoupon;
import com.askdog.service.exception.ForbiddenException;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.impl.cell.CouponCell;
import com.askdog.service.impl.cell.UserCouponCell;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

import static com.askdog.model.common.State.DELETED;
import static com.askdog.model.entity.inner.coupon.CouponState.NOT_USED;
import static com.askdog.model.entity.inner.coupon.CouponState.USED;
import static com.askdog.model.entity.inner.coupon.CouponType.FORWARDED;
import static com.askdog.model.entity.inner.coupon.CouponType.NORMAL;
import static com.askdog.service.bo.common.PagedDataUtils.rePage;
import static com.askdog.service.exception.ForbiddenException.Error.*;
import static com.askdog.service.exception.NotFoundException.Error.*;
import static com.google.common.collect.Sets.newHashSet;


@RestController
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private CouponCell couponCell;
    @Autowired
    private UserService userService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserCouponCell userCouponCell;

    @Nonnull
    @Override
    public CouponDetail getDetail(@PathVariable("id") long id) {
        CouponDetailBasic couponDetailBasic = couponCell.getBasic(id);
        UserDetail userDetail = couponDetailBasic.getUpdateUserId() == null ? null : userService.findDetail(couponDetailBasic.getUpdateUserId());
        CouponDetail couponDetail = new CouponDetail();
        couponDetail.setId(couponDetailBasic.getId());
        couponDetail.setCreationTime(couponDetailBasic.getCreationTime());
        couponDetail.setLastUpdateTime(couponDetailBasic.getLastUpdateTime());
        couponDetail.setName(couponDetailBasic.getName());
        couponDetail.setCouponRule(couponDetailBasic.getCouponRule());
        couponDetail.setType(couponDetailBasic.getType());
        couponDetail.setUpdateUser(userDetail);
        couponDetail.setStore(storeService.findDetail(couponDetailBasic.getStoreId(), true));
        return couponDetail;
    }

    @Nonnull
    @Override
    public CouponPageDetail create(@RequestParam(name = "userId") Long userId, @RequestBody PureCoupon pureCoupon) {
        Store store;
        if (!couponCell.isAdmin(userId)) {
            store = storeRepository.findByIdAndOwner_IdAndStateNotIn(pureCoupon.getStoreId(), userId, newHashSet(DELETED)).orElseThrow(() -> new ForbiddenException(CREATE_COUPONS));
        } else {
            store = storeRepository.findByIdAndStateNotIn(pureCoupon.getStoreId(), newHashSet(DELETED)).orElseThrow(() -> new NotFoundException(STORE));
        }
        Coupon coupon = new Coupon();
        coupon.setName(pureCoupon.getName());
        coupon.setStore(store);
        coupon.setRule(pureCoupon.getRule());
        coupon.setType(pureCoupon.getType());
        coupon.setCreationTime(new Date());
        Coupon savedCoupon = couponRepository.save(coupon);
        return new CouponPageDetail().from(getDetail(savedCoupon.getId()));
    }

    @Override
    public void deleteCoupon(@PathVariable("id") long couponId, @RequestParam("userId") long userId) {
        boolean enableDelete = false;
        Optional<Coupon> coupon = couponRepository.findById(couponId);
        if (coupon.isPresent()) {
            Store store = coupon.get().getStore();
            enableDelete = store.getOwner().getId() == userId || store.getAgent().getOwner().getId() == userId;
        }
        Conditions.checkState(enableDelete, () -> new ForbiddenException(DELETE_COUPON));
        couponRepository.delete(couponId);
        couponCell.refreshCouponDetailBasic(couponId);
    }

    @Nonnull
    @Override
    public UserCouponConsumeDetail consume(@RequestParam("userId") long userId, @PathVariable("id") long userCouponId) {
        UserCoupon userCoupon;
        if (couponCell.isAdmin(userId)) {
            userCoupon = userCouponRepository.findById(userCouponId).orElseThrow(() -> new NotFoundException(USER_COUPON));
        } else {
            userCoupon = userCouponRepository.findByCoupon_Store_Owner_IdAndId(userId, userCouponId).orElseThrow(() -> new ForbiddenException(CONSUME_COUPON));
        }
        Conditions.checkState(userCoupon.getState() == NOT_USED, () -> new ForbiddenException(HAS_USED));
        userCoupon.setState(USED);
        userCoupon.setUseTime(new Date());
        UserCoupon savedUserCoupon = userCouponRepository.save(userCoupon);

        userCouponCell.refreshUserCouponBasicCache(savedUserCoupon.getId());
        return new UserCouponConsumeDetail().from(savedUserCoupon);
    }

    @Nonnull
    @Override
    public PagedData<CouponStatisticsDetail> getCoupons(@RequestParam("userId") long userId,
                                                        @RequestParam("storeId") long storeId,
                                                        @RequestBody Pageable pageable) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new NotFoundException(STORE));
        if (!couponCell.isAdmin(userId)) {
            Conditions.checkState(store.getOwner().getId() == userId, () -> new ForbiddenException(COUPON_lIST));
        }
        Page<Coupon> coupons = couponRepository.findByStore_Id(storeId, pageable);
        List<UserCoupon> userCoupons = userCouponRepository.findByCoupon_IdIn(coupons.getContent().stream().map(Coupon::getId).collect(Collectors.toList()));
        Map<Long, Map<CouponState, Long>> couponStatisticsMap = userCoupons.stream()
                .collect(Collectors.groupingBy(each -> each.getCoupon().getId(), Collectors.groupingBy(UserCoupon::getState, Collectors.counting())));

        return rePage(coupons, pageable, coupon -> {
            CouponStatisticsDetail couponStatisticsDetail = new CouponStatisticsDetail().from(coupon);
            Map<CouponState, Long> couponStateCountMap = couponStatisticsMap.get(coupon.getId());
            if (couponStateCountMap != null) {
                couponStateCountMap.putIfAbsent(USED, 0L);
                couponStateCountMap.putIfAbsent(NOT_USED, 0L);
                couponStatisticsDetail.setUseCount(couponStateCountMap.get(USED));
                couponStatisticsDetail.setNotUseCount(couponStateCountMap.get(NOT_USED));
            }
            return couponStatisticsDetail;
        });
    }

    @Nonnull
    @Override
    public Map<CouponType, Map<CouponState, Long>> getStoreStatistics(@RequestParam("userId") long userId,
                                                                      @RequestParam("storeId") long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new NotFoundException(STORE));
        if (!couponCell.isAdmin(userId)) {
            Conditions.checkState(store.getOwner().getId() == userId, () -> new ForbiddenException(COUPON_lIST));
        }
        List<UserCoupon> userCouponList = userCouponRepository.findByProduct_IdIn(getStoreProduct(storeId));
        Map<CouponType, Map<CouponState, Long>> storeStatisticsMap = userCouponList.stream()
                .collect(Collectors.groupingBy(each -> each.getCoupon().getType(), Collectors.groupingBy(UserCoupon::getState, Collectors.counting())));
        storeStatisticsMap.keySet().stream()
                .forEach(each -> {
                    Map<CouponState, Long> map = storeStatisticsMap.get(each);
                    map.putIfAbsent(USED, 0L);
                    map.putIfAbsent(NOT_USED, 0L);
                });
        storeStatisticsMap.putIfAbsent(NORMAL, ImmutableMap.of(USED, 0L, NOT_USED, 0L));
        storeStatisticsMap.putIfAbsent(FORWARDED, ImmutableMap.of(USED, 0L, NOT_USED, 0L));
        return storeStatisticsMap;
    }

    private List<Long> getStoreProduct(Long storeId) {
        int PAGE = 0;
        int SIZE = 10;
        List<Long> productList = new ArrayList<>();
        Page<Product> products;
        Pageable pageable = new PageRequest(PAGE, SIZE);
        do {
            products = productRepository.findByStore_Id(storeId, pageable);
            productList.addAll(products.getContent().stream().map(Product::getId).collect(Collectors.toList()));
            pageable = pageable.next();
            PAGE++;
        } while (products.hasNext());
        return productList;
    }


}

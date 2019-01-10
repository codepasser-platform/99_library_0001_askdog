package com.askdog.service.impl;

import com.askdog.common.utils.Conditions;
import com.askdog.dao.repository.ProductRepository;
import com.askdog.dao.repository.UserCouponRepository;
import com.askdog.dao.repository.UserRepository;
import com.askdog.dao.repository.extend.CouponStatisticsRepository;
import com.askdog.model.entity.Coupon;
import com.askdog.model.entity.Product;
import com.askdog.model.entity.User;
import com.askdog.model.entity.UserCoupon;
import com.askdog.model.entity.inner.coupon.CouponState;
import com.askdog.service.*;
import com.askdog.service.bo.admin.dashboard.CouponStatistics;
import com.askdog.service.bo.common.PagedData;
import com.askdog.service.bo.common.PagedDataUtils;
import com.askdog.service.bo.coupon.CouponDetail;
import com.askdog.service.bo.usercoupon.UserCouponBasic;
import com.askdog.service.bo.usercoupon.UserCouponDetail;
import com.askdog.service.bo.usercoupon.UserCouponPageDetail;
import com.askdog.service.exception.ConflictException;
import com.askdog.service.exception.ForbiddenException;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.impl.cell.UserCouponCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Optional;

import static com.askdog.common.utils.Conditions.checkState;
import static com.askdog.model.common.State.DELETED;
import static com.askdog.model.entity.inner.coupon.CouponState.NOT_USED;
import static com.askdog.model.entity.inner.coupon.CouponType.FORWARDED;
import static com.askdog.service.exception.ForbiddenException.Error.COUPON_DETAIL;
import static com.askdog.service.exception.NotFoundException.Error.*;
import static com.google.common.collect.Sets.newHashSet;

@RestController
public class UserCouponServiceImpl implements UserCouponService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCouponCell userCouponCell;
    @Autowired
    private CouponService couponService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Autowired
    private CouponStatisticsRepository couponRegistrationStatistics;

    @Nonnull
    @Override
    public UserCouponDetail getDetail(@RequestParam("userId") long userId, @PathVariable("id") long id) {
        UserCouponBasic userCouponBasic = userCouponCell.getBasic(id);
        Conditions.checkState(userCouponBasic.getUserId() == userId, () -> new ForbiddenException(COUPON_DETAIL));
        CouponDetail couponDetail = couponService.getDetail(userCouponBasic.getCouponId());
        UserCouponDetail userCouponDetail = new UserCouponDetail();
        userCouponDetail.setId(userCouponBasic.getId());
        userCouponDetail.setCreationTime(userCouponBasic.getCreationTime());
        userCouponDetail.setCouponState(userCouponBasic.getState());
        userCouponDetail.setUseTime(userCouponBasic.getUseTime());
        userCouponDetail.setCouponDetail(couponDetail);
        userCouponDetail.setProductDetail(productService.getDetail(userId, userCouponBasic.getProductId(), true));
        userCouponDetail.setUserDetail(userService.findDetail(userCouponBasic.getUserId()));
        userCouponDetail.setStoreDetail(storeService.findDetail(couponDetail.getStore().getId(), true));
        return userCouponDetail;
    }

    @Nonnull
    @Override
    public UserCouponPageDetail getPageDetail(@RequestParam("userId") long userId, @PathVariable("id") long id) {
        return new UserCouponPageDetail().from(getDetail(userId, id));
    }

    @Nonnull
    @Override
    public UserCouponPageDetail gain(@NotNull @RequestParam("userId") long userId, @NotNull @RequestParam("productId") long productId, @NotNull @RequestParam("couponId") long couponId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER));
        Product product = productRepository.findByIdAndStateNotIn(productId, newHashSet(DELETED)).orElseThrow(() -> new NotFoundException(PRODUCT));
        Coupon coupon = product.getCoupons().stream().filter(c -> c.getId() == couponId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(COUPON));

        // 检查是否重复领取
        Optional<UserCoupon> foundUserCoupon = userCouponRepository.findByUser_IdAndCoupon_IdAndProduct_Id(userId, couponId, productId);
        if (foundUserCoupon.isPresent()) {
            throw new ConflictException(ConflictException.Error.USER_COUPON);
        }

        return createUserCoupon(user, product, coupon, NOT_USED);
    }

    @Nonnull
    @Override
    public UserCouponPageDetail upgradeUserCoupon(@NotNull @RequestParam("userId") long userId, @NotNull @RequestParam("productId") long productId, @NotNull @RequestParam("couponId") long couponId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER));
        Product product = productRepository.findByIdAndStateNotIn(productId, newHashSet(DELETED)).orElseThrow(() -> new NotFoundException(PRODUCT));

        Optional<com.askdog.model.entity.UserCoupon> foundUserCoupon = userCouponRepository.findByUser_IdAndCoupon_IdAndProduct_Id(userId, couponId, productId);
        if (foundUserCoupon.isPresent()) {
            Coupon coupon = foundUserCoupon.get().getCoupon();
            if (coupon.getType() == FORWARDED) {
                throw new ConflictException(ConflictException.Error.USER_COUPON);
            }
        }

        Coupon forwardedCoupon = product.getCoupons().stream().filter(c -> c.getType() == FORWARDED).findFirst()
                .orElseThrow(() -> new NotFoundException(COUPON));
        return createUserCoupon(user, product, forwardedCoupon, NOT_USED);
    }

    @Override
    public void deleteUserCoupon(@RequestParam("userId") long userId, @PathVariable("id") long id) {
        checkState(userCouponRepository.findByUser_IdAndCoupon_Id(userId, id).isPresent(), () -> new NotFoundException(USER_COUPON));
        userCouponRepository.deleteById(id);
        userCouponCell.refreshUserCouponBasicCache(id);

    }

    @Nonnull
    @Override
    public PagedData<UserCouponPageDetail> getUserCoupons(@RequestParam(name = "userId") Long userId,
                                                          @RequestBody() Pageable pageable) {

        //Page<UserCoupon> userCoupons = userCouponRepository.findByUser_Id(userId, pageable);
        Page<UserCoupon> userCoupons = userCouponRepository.findByUser_IdOrderByStateAsc(userId, pageable);
        return PagedDataUtils.rePage(userCoupons, pageable, coupon -> new UserCouponPageDetail().from(getDetail(userId, coupon.getId())));
    }


    @Override
    public CouponStatistics couponStatistic() {
        CouponStatistics couponStatistics = new CouponStatistics();
        couponStatistics.setTotalCouponCount(userCouponRepository.countCreatedCoupon());
        couponStatistics.setCouponCreationTrend(couponRegistrationStatistics.couponRegistrationStatistics("day", "1 years"));
        return couponStatistics;
    }

    private UserCouponPageDetail createUserCoupon(User user, Product product, Coupon coupon, CouponState state) {
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUser(user);
        userCoupon.setCoupon(coupon);
        userCoupon.setProduct(product);
        userCoupon.setState(state);
        userCoupon.setCreationTime(new Date());
        UserCoupon savedUserCoupon = userCouponRepository.save(userCoupon);

        return new UserCouponPageDetail().from(getDetail(user.getId(), savedUserCoupon.getId()));
    }

}

package com.askdog.dao.repository;


import com.askdog.model.entity.UserCoupon;
import com.askdog.model.entity.inner.coupon.CouponState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends CrudRepository<UserCoupon, Long>, JpaSpecificationExecutor<UserCoupon> {

    Page<UserCoupon> findByUser_IdAndState(long userId, CouponState couponState, Pageable pageable);

    @Query(value = "SELECT c FROM UserCoupon c INNER JOIN c.user_id u WHERE u.id = :userId ORDER BY c.state DESC , c.creation_time", nativeQuery = true)
    List<UserCoupon> findByUser_Id(@Param(value = "userId") long userId);

    Optional<UserCoupon> findById(long id);

    Optional<UserCoupon> findByCoupon_Store_Owner_IdAndId(long userId, long id);

    Optional<UserCoupon> findByUser_IdAndCoupon_Id(long userId, long couponId);

    Optional<UserCoupon> findByUser_IdAndCoupon_IdAndProduct_Id(long userId, long couponId, long productId);

    Page<UserCoupon> findByUser_IdOrderByStateAsc(long userId, Pageable pageable);

    List<UserCoupon> findByProduct_IdIn(List<Long> productIdList);

    List<UserCoupon> findByCoupon_IdIn(List<Long> couponIdList);

    void deleteById(long couponId);

    @Query(value = "SELECT count(*) FROM mc_user_coupon ", nativeQuery = true)
    Long countCreatedCoupon();

}

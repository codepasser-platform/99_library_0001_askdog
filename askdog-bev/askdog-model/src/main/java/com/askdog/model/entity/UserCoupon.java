package com.askdog.model.entity;

import com.askdog.model.entity.inner.coupon.CouponState;
import com.askdog.model.entity.partial.TimeBasedStatistics;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "mc_user_coupon")
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "CouponCreationInWeek",
                classes = {
                        @ConstructorResult(targetClass = TimeBasedStatistics.class, columns = {
                                @ColumnResult(name = "day", type = String.class),
                                @ColumnResult(name = "coupon_count", type = Long.class)
                        })
                }
        )
})
public class UserCoupon extends Base {

    private static final long serialVersionUID = -9103770339156446578L;

    @NotNull
    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @NotNull
    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "coupon_id", nullable = false, updatable = false)
    private Coupon coupon;

    @NotNull
    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private Product product;

    @NotNull
    @Enumerated(STRING)
    @Column(name = "state")
    private CouponState state;

    @NotNull
    @Column(name = "creation_time")
    private Date creationTime;

    @Column(name = "use_time")
    private Date useTime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CouponState getState() {
        return state;
    }

    public void setState(CouponState state) {
        this.state = state;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }
}

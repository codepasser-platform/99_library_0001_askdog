package com.askdog.coupon.store.web;

import com.askdog.oauth.client.vo.UserInfo;
import com.askdog.service.StoreService;
import com.askdog.service.UserCouponService;
import com.askdog.service.bo.BasicUser;
import com.askdog.service.bo.admin.dashboard.CouponStatistics;
import com.askdog.service.bo.admin.dashboard.StoreStatistics;
import com.askdog.service.bo.common.PagedData;
import com.askdog.service.bo.store.AmendedStore;
import com.askdog.service.bo.store.PureStore;
import com.askdog.service.bo.store.StoreDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.util.List;

import static com.askdog.service.utils.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/stores")
public class StoreApi {

    private static final String PRE_AUTHORIZE = "hasAnyRole('ROLE_ADMIN', 'ROLE_AGENT_ADMIN', 'ROLE_AGENT_EMPLOYEE')";

    @Autowired private StoreService storeService;
    @Autowired private UserCouponService userCouponService;

    @RequestMapping(value = "/version")
    public String version() {
        return "2016年9月30日00:00:00";
    }

    @PreAuthorize(PRE_AUTHORIZE)
    @RequestMapping(value = "/list", method = GET)
    public PagedData<StoreDetail> getStoreList(@AuthenticationPrincipal UserInfo userInfo,
                                               @PageableDefault(sort = "creationTime", direction = DESC) Pageable pageable) {
        return storeService.getStoreByRole(userInfo.getId(), pageable);
    }

    @Nonnull
    @PreAuthorize(PRE_AUTHORIZE)
    @RequestMapping(value = "/{storeId}", method = GET)
    public StoreDetail getStoreDetail(@AuthenticationPrincipal UserInfo userInfo, @PathVariable("storeId") long storeId) {
        return storeService.findPageDetail(userInfo.getId(), storeId);
    }

    @PreAuthorize(PRE_AUTHORIZE)
    @RequestMapping(method = POST)
    public StoreDetail create(@AuthenticationPrincipal UserInfo userInfo, @Valid @RequestBody PureStore pureStore) {
        return storeService.create(userInfo.getId(), pureStore);
    }

    @PreAuthorize(PRE_AUTHORIZE)
    @RequestMapping(value = "/{storeId}", method = PUT)
    public StoreDetail update(@AuthenticationPrincipal UserInfo userInfo, @PathVariable("storeId") Long storeId,
                              @Valid @RequestBody AmendedStore amendedStore) {
        return storeService.update(userInfo.getId(), storeId, amendedStore);
    }

    @PreAuthorize(PRE_AUTHORIZE)
    @RequestMapping(value = "/{storeId}", method = DELETE)
    public void delete(@AuthenticationPrincipal UserInfo userInfo, @PathVariable("storeId") Long storeId) {
        storeService.delete(userInfo.getId(), storeId);
    }

    @PreAuthorize(PRE_AUTHORIZE)
    @RequestMapping(value = "/search/{key}", produces = APPLICATION_JSON_UTF8_VALUE)
    public List<BasicUser> search(@PathVariable("key") String key) {
        return storeService.search(key);
    }

    @PreAuthorize(PRE_AUTHORIZE)
    @RequestMapping(value = "/storedata", produces = APPLICATION_JSON_UTF8_VALUE)
    public StoreStatistics store() {
        return storeService.storeStatistic();
    }

    @PreAuthorize(PRE_AUTHORIZE)
    @RequestMapping(value = "/coupondata", produces = APPLICATION_JSON_UTF8_VALUE)
    public CouponStatistics coupon() {
        return userCouponService.couponStatistic();
    }
}

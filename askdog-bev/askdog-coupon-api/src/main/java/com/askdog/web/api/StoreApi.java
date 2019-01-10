package com.askdog.web.api;

import com.askdog.service.StoreService;
import com.askdog.service.bo.common.PagedData;
import com.askdog.service.bo.store.StoreBasic;
import com.askdog.service.bo.store.StoreDetail;
import com.askdog.web.configuration.userdetails.AdUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import static com.askdog.web.utils.HeaderUtils.getRequestRealIp;
import static org.springframework.data.domain.Sort.Direction.DESC;


@RestController
@RequestMapping("/api/stores")
public class StoreApi {

    @Autowired
    private StoreService storeService;

    @RequestMapping(value = "/version")
    public String version() {
        return "2016年10月9日00:00:00";
    }

    @Nonnull
    @RequestMapping(method = RequestMethod.GET)
    PagedData<StoreBasic> getStores(HttpServletRequest request,
                                    @RequestParam(name = "lat", required = false) Double lat,
                                    @RequestParam(name = "lng", required = false) Double lng,
                                    @PageableDefault(sort = "creationTime", direction = DESC) Pageable pageable) {
        return storeService.getStores(getRequestRealIp(request), lat, lng, pageable);
    }

    @Nonnull
    @RequestMapping(value = "/{storeId}", method = RequestMethod.GET)
    StoreDetail getStoreDetail(@AuthenticationPrincipal AdUserDetails user, @PathVariable("storeId") long storeId) {
        return storeService.findPageDetail(user != null ? user.getId() : null, storeId);
    }

}

package com.stock.capital.ipo.console.govt;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.common.service.response.PagedData;
import com.stock.capital.ipo.service.UserMgrService;
import com.stock.capital.ipo.service.vo.UserBasic;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * [UserApi].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@RestController
@RequestMapping("/govt/user/mgr")
public class UserMgrApi {

  @Autowired private UserMgrService userMgrService;

  @Nonnull
  @RequestMapping(value = "/pageable", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  PagedData<UserBasic> pageable(@PageableDefault Pageable pageable) throws ServiceException {
    // TODO issue paging sorting conditions cannot be passed with FeignClient
    // PageRequest pageRequest = PageRequest.of(1, 3, Sort.Direction.DESC, "registrationTime");
    return userMgrService.pageable(pageable);
  }

  @Nonnull
  @RequestMapping(value = "/pagination", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  PagedData<UserBasic> pagination(@RequestParam("page") int page, @RequestParam("size") int size)
      throws ServiceException {
    return userMgrService.pageRequest(page, size);
  }
}

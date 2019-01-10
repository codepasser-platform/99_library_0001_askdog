package com.stock.capital.ipo.service;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.common.service.response.AssertResponse;
import com.stock.capital.common.service.response.PagedData;
import com.stock.capital.ipo.service.vo.UserBasic;
import java.util.List;
import javax.annotation.Nonnull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * [UserMgrService].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@FeignClient("ipo-service")
@RequestMapping("/ipo/user/mgr")
public interface UserMgrService {

  @Nonnull
  @RequestMapping(value = "/{userId}", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  UserBasic validById(@PathVariable("userId") long userId) throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  List<UserBasic> list() throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/pageable", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  PagedData<UserBasic> pageable(@Nonnull @RequestBody Pageable pageable) throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/pageRequest", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  PagedData<UserBasic> pageRequest(@RequestParam("page") int page, @RequestParam("size") int size)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/", method = DELETE, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse removeByUsername(@Nonnull @RequestParam("username") String username)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/exists", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse existsById(@RequestParam("userId") long userId) throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/exists/username", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse existsByUsername(@Nonnull @RequestParam("username") String userName)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/exists/phone", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse existsByPhoneNumber(@Nonnull @RequestParam("phoneNumber") String phoneNumber)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/exists/email", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse existsByEmail(@Nonnull @RequestParam("email") String email)
      throws ServiceException;
}

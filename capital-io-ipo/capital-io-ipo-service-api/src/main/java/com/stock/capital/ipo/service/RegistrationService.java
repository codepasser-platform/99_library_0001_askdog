package com.stock.capital.ipo.service;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.common.service.response.AssertResponse;
import com.stock.capital.ipo.service.bo.PhoneUserCreation;
import com.stock.capital.ipo.service.bo.UserAttributeCreation;
import com.stock.capital.ipo.service.vo.IdentifyingCode;
import javax.annotation.Nonnull;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * [RegistrationService].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@FeignClient("ipo-service")
@RequestMapping("/ipo/user/registration")
public interface RegistrationService {

  @Nonnull
  @RequestMapping(
      value = "/mobile/identifying",
      method = GET,
      produces = APPLICATION_JSON_UTF8_VALUE)
  IdentifyingCode sendIdentifyingCode(@Nonnull @RequestParam("phone") String phone)
      throws ServiceException;

  @Nonnull
  @RequestMapping(
      value = "/mobile/identifying",
      method = POST,
      produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse checkIdentifyingCode(
      @Nonnull @RequestParam("phone") String phone, @Nonnull @RequestParam("code") String code)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/mobile", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse registrationPhoneUser(
      @Nonnull @Valid @RequestBody PhoneUserCreation phoneUserCreation) throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/attribute", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse registrationUserAttribute(
      @Nonnull @Valid @RequestBody UserAttributeCreation userAttributeCreation)
      throws ServiceException;
}

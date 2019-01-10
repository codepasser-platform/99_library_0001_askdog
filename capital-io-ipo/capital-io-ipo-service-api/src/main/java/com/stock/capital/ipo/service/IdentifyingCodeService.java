package com.stock.capital.ipo.service;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.common.service.response.AssertResponse;
import com.stock.capital.ipo.service.vo.IdentifyingCode;
import javax.annotation.Nonnull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * [RegistrationService].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@FeignClient("ipo-service")
@RequestMapping("/ipo/identifying-code")
public interface IdentifyingCodeService {

  @Nonnull
  @RequestMapping(value = "/phone", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  IdentifyingCode sendIdentifyingCode(@Nonnull @RequestParam("phone") String phone)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/phone", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse checkIdentifyingCode(
      @Nonnull @RequestParam("phone") String phone, @Nonnull @RequestParam("code") String code)
      throws ServiceException;
}

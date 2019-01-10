package com.stock.capital.ipo.service.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableBiMap.of;
import static com.stock.capital.common.business.category.IdentifyingCodeType.REGISTRATION;
import static com.stock.capital.common.model.RegexPattern.REGEX_IDENTIFYING_CODE;
import static com.stock.capital.common.model.RegexPattern.REGEX_PHONE;
import static com.stock.capital.common.service.exception.IllegalTermsException.Error.IDENTIFYING_CODE;

import com.stock.capital.common.exception.CommonException;
import com.stock.capital.common.processor.annotation.InjectLogger;
import com.stock.capital.common.service.exception.IllegalTermsException;
import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.common.service.response.AssertResponse;
import com.stock.capital.ipo.dao.repository.redis.IdentifyingCodeRepository;
import com.stock.capital.ipo.service.IdentifyingCodeService;
import com.stock.capital.ipo.service.vo.IdentifyingCode;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * [RegistrationServiceImpl].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@Service
@RestController
public class IdentifyingCodeServiceImpl implements IdentifyingCodeService {

  @InjectLogger private Logger logger;

  @Autowired private IdentifyingCodeRepository identifyingCodeRepository;

  @Nonnull
  @Override
  public IdentifyingCode sendIdentifyingCode(@Nonnull @RequestParam("phone") String phone)
      throws ServiceException {
    checkArgument(phone.matches(REGEX_PHONE), "invalid phone number");

    try {
      // 索取验证码
      Map<String, String> values =
          of("phone", phone, "issue_time", String.valueOf(new Date().getTime()));
      String code =
          identifyingCodeRepository.claimIdentifyingCode(
              phone, REGISTRATION, values, 5, TimeUnit.MINUTES);
      IdentifyingCode identifyingCode = new IdentifyingCode();
      identifyingCode.setCode(code);
      identifyingCode.setIdentifyingCodeType(REGISTRATION);
      return identifyingCode;
    } catch (CommonException e) {
      throw new IllegalTermsException(IDENTIFYING_CODE);
    }
  }

  @Nonnull
  @Override
  public AssertResponse checkIdentifyingCode(
      @Nonnull @RequestParam("phone") String phone, @Nonnull @RequestParam("code") String code)
      throws ServiceException {
    checkArgument(code.matches(REGEX_IDENTIFYING_CODE), "invalid identifying code");
    return new AssertResponse(
        identifyingCodeRepository.isIdentifyingCodeValidate(REGISTRATION, phone, code));
  }
}

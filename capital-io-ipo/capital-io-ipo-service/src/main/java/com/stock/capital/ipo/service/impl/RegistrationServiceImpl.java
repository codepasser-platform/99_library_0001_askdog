package com.stock.capital.ipo.service.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.stock.capital.common.model.RegexPattern.REGEX_PHONE;
import static com.stock.capital.common.service.exception.ConflictException.Error.USER_PHONE;
import static com.stock.capital.common.service.exception.ConflictException.Error.USER_USERNAME;
import static com.stock.capital.common.service.exception.IllegalArgumentException.Error.INVALID_IDENTIFYING_CODE;
import static com.stock.capital.common.utils.Conditions.checkState;
import static com.stock.capital.ipo.model.entity.inner.UserStatus.MANAGED;
import static com.stock.capital.ipo.model.entity.inner.UserStatus.PHONE_CONFIRMED;

import com.stock.capital.common.processor.annotation.InjectLogger;
import com.stock.capital.common.service.exception.ConflictException;
import com.stock.capital.common.service.exception.IllegalArgumentException;
import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.common.service.response.AssertResponse;
import com.stock.capital.ipo.dao.repository.UserRepository;
import com.stock.capital.ipo.dao.repository.mongo.UserAttributeRepository;
import com.stock.capital.ipo.model.data.UserAttribute;
import com.stock.capital.ipo.model.entity.User;
import com.stock.capital.ipo.service.IdentifyingCodeService;
import com.stock.capital.ipo.service.RegistrationService;
import com.stock.capital.ipo.service.bo.PhoneUserCreation;
import com.stock.capital.ipo.service.bo.UserAttributeCreation;
import com.stock.capital.ipo.service.impl.cell.UserCell;
import com.stock.capital.ipo.service.vo.IdentifyingCode;
import java.util.Date;
import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nonnull;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
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
public class RegistrationServiceImpl implements RegistrationService {

  @InjectLogger private Logger logger;

  @Autowired private UserCell userCell;

  @Autowired private IdentifyingCodeService identifyingCodeService;

  @Autowired private UserRepository userRepository;

  @Autowired private UserAttributeRepository userAttributeRepository;

  @Nonnull
  @Override
  public IdentifyingCode sendIdentifyingCode(@Nonnull @RequestParam("phone") String phone)
      throws ServiceException {
    checkArgument(phone.matches(REGEX_PHONE), "invalid phone number");
    // 校验手机号码
    checkState(!userCell.existByPhoneNumber(phone), () -> new ConflictException(USER_PHONE));

    return identifyingCodeService.sendIdentifyingCode(phone);
  }

  @Nonnull
  @Override
  public AssertResponse checkIdentifyingCode(
      @Nonnull @RequestParam("phone") String phone, @Nonnull @RequestParam("code") String code)
      throws ServiceException {
    return identifyingCodeService.checkIdentifyingCode(phone, code);
  }

  @Nonnull
  @Override
  @Transactional
  public AssertResponse registrationPhoneUser(
      @Nonnull @Valid @RequestBody PhoneUserCreation phoneUserCreation) throws ServiceException {

    // 校验手机号码
    checkState(
        !userCell.existByPhoneNumber(phoneUserCreation.getPhoneNumber()),
        () -> new ConflictException(USER_PHONE));

    checkState(
        !userCell.existByUsername(phoneUserCreation.getPhoneNumber()),
        () -> new ConflictException(USER_USERNAME));

    // 校验认证码
    checkState(
        identifyingCodeService
            .checkIdentifyingCode(
                phoneUserCreation.getPhoneNumber(), phoneUserCreation.getIdentifyingCode())
            .isSuccess(),
        () -> new IllegalArgumentException(INVALID_IDENTIFYING_CODE));

    User creationUser = phoneUserCreation.convert();
    creationUser.setUserStatuses(EnumSet.of(PHONE_CONFIRMED, MANAGED));
    creationUser.setRegistrationTime(new Date());
    creationUser.setCreationTime(new Date());
    creationUser.setUpdateTime(new Date());
    userRepository.save(creationUser);

    return new AssertResponse(true);
  }

  @Nonnull
  @Override
  public AssertResponse registrationUserAttribute(
      @Nonnull @Valid @RequestBody UserAttributeCreation userAttributeCreation)
      throws ServiceException {
    userCell.validById(userAttributeCreation.getUserId());

    AtomicReference<String> userAttributeId = new AtomicReference();
    userAttributeRepository
        .findByUserId(userAttributeCreation.getUserId())
        .ifPresent(
            (userAttribute -> {
              userAttributeId.set(userAttribute.getId());
            }));
    UserAttribute userAttribute = userAttributeCreation.convert();
    userAttribute.setId(userAttributeId.get());
    userAttributeRepository.save(userAttribute);

    return new AssertResponse(true);
  }
}

package com.stock.capital.ipo.service.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Sets.newHashSet;
import static com.stock.capital.common.model.RegexPattern.REGEX_MAIL;
import static com.stock.capital.common.model.RegexPattern.REGEX_PHONE;
import static com.stock.capital.common.model.RegexPattern.REGEX_USER_NAME;
import static com.stock.capital.common.model.entity.inner.State.DELETED;
import static com.stock.capital.common.service.helper.PagedDataUtils.rePage;

import com.google.common.collect.Lists;
import com.stock.capital.common.processor.annotation.InjectLogger;
import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.common.service.response.AssertResponse;
import com.stock.capital.common.service.response.PagedData;
import com.stock.capital.ipo.dao.repository.UserRepository;
import com.stock.capital.ipo.model.entity.User;
import com.stock.capital.ipo.service.UserMgrService;
import com.stock.capital.ipo.service.impl.cell.UserCell;
import com.stock.capital.ipo.service.vo.UserBasic;
import java.util.List;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * [UserMgrServiceImpl].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@Service
@RestController
public class UserMgrServiceImpl implements UserMgrService {

  @InjectLogger private Logger logger;

  @Autowired private UserCell userCell;

  @Autowired private UserRepository userRepository;

  @Nonnull
  @Override
  @Transactional
  public AssertResponse removeByUsername(@Nonnull @RequestParam("username") String username)
      throws ServiceException {
    return new AssertResponse(userRepository.deleteByUsername(username) > 0);
  }

  @Nonnull
  @Override
  public UserBasic validById(@PathVariable("userId") long userId) throws ServiceException {
    return new UserBasic().from(userCell.validById(userId));
  }

  @Nonnull
  @Override
  public List<UserBasic> list() throws ServiceException {
    List<UserBasic> users = Lists.newArrayList();
    userRepository.findAll().forEach((item) -> users.add(new UserBasic().from(item)));
    return users;
  }

  @Nonnull
  @Override
  public PagedData<UserBasic> pageable(@Nonnull @RequestBody Pageable pageable)
      throws ServiceException {
    Page<User> users = userRepository.findAllByStateNotIn(newHashSet(DELETED), pageable);
    return rePage(users, pageable, user -> new UserBasic().from(user));
  }

  @Nonnull
  @Override
  public PagedData<UserBasic> pageRequest(
      @RequestParam("page") int page, @RequestParam("size") int size) throws ServiceException {
    Sort sort = new Sort(Sort.Direction.ASC, "registrationTime");
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<User> users = userRepository.findAllByStateNotIn(newHashSet(DELETED), pageable);
    return rePage(users, pageable, user -> new UserBasic().from(user));
  }

  @Nonnull
  @Override
  public AssertResponse existsById(@RequestParam("userId") long userId) throws ServiceException {
    return new AssertResponse(userCell.existById(userId));
  }

  @Nonnull
  @Override
  public AssertResponse existsByUsername(@Nonnull @RequestParam("username") String userName)
      throws ServiceException {
    checkArgument(userName.matches(REGEX_USER_NAME), "invalid user name");
    return new AssertResponse(userCell.existByUsername(userName));
  }

  @Nonnull
  @Override
  public AssertResponse existsByPhoneNumber(
      @Nonnull @RequestParam("phoneNumber") String phoneNumber) throws ServiceException {
    checkArgument(phoneNumber.matches(REGEX_PHONE), "invalid phone number");
    return new AssertResponse(userCell.existByPhoneNumber(phoneNumber));
  }

  @Nonnull
  @Override
  public AssertResponse existsByEmail(@Nonnull @RequestParam("email") String email)
      throws ServiceException {
    checkArgument(email.matches(REGEX_MAIL), "invalid email address");
    return new AssertResponse(userCell.existByEmail(email));
  }
}

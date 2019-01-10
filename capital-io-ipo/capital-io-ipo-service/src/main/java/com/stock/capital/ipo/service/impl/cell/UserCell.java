package com.stock.capital.ipo.service.impl.cell;

import static com.stock.capital.common.service.exception.NotFoundException.Error.USER;

import com.stock.capital.common.service.exception.NotFoundException;
import com.stock.capital.ipo.dao.repository.UserRepository;
import com.stock.capital.ipo.model.entity.User;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * [UserCell].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@Component
public class UserCell {

  @Autowired private UserRepository userRepository;

  public User validById(long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER));
  }

  public boolean existById(long userId) {
    return userRepository.findById(userId).isPresent();
  }

  public boolean existByUsername(@Nonnull String userName) {
    return userRepository.findByUsername(userName).isPresent();
  }

  public boolean existByPhoneNumber(@Nonnull String phoneNumber) {
    return userRepository.findByPhoneNumber(phoneNumber).isPresent();
  }

  public boolean existByEmail(@Nonnull String email) {
    return userRepository.findByEmail(email).isPresent();
  }
}

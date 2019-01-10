package com.stock.capital.ipo.model.validation;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.springframework.beans.BeanUtils.findDeclaredMethod;

import com.stock.capital.common.processor.annotation.InjectLogger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;

/**
 * [UserIdentifierValidator].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class UserIdentifierValidator implements ConstraintValidator<UserIdentifier, Object> {

  @InjectLogger private Logger logger;

  private String emailMethodName;
  private String phoneNumberMethodName;

  @Override
  public void initialize(UserIdentifier constraintAnnotation) {
    this.emailMethodName = constraintAnnotation.emailMethod();
    this.phoneNumberMethodName = constraintAnnotation.phoneNumberMethod();
  }

  @Override
  public boolean isValid(Object userEntity, ConstraintValidatorContext context) {
    String email = invoke(userEntity, findDeclaredMethod(userEntity.getClass(), emailMethodName));
    String phoneNumber =
        invoke(userEntity, findDeclaredMethod(userEntity.getClass(), phoneNumberMethodName));
    return !isNullOrEmpty(email) || !isNullOrEmpty(phoneNumber);
  }

  private <T> T invoke(Object userEntity, Method emailMethod) {
    Object result = null;
    try {
      result = emailMethod.invoke(userEntity);
    } catch (IllegalAccessException | InvocationTargetException e) {
      logger.error(e.getLocalizedMessage());
    }

    //noinspection unchecked
    return (T) result;
  }
}

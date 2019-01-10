package com.stock.capital.ipo.model.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.stock.capital.common.model.entity.converter.JsonTypeReferenceConverter;
import com.stock.capital.ipo.model.entity.inner.UserStatus;
import java.util.EnumSet;

/**
 * [UserStatusSetConverter].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class UserStatusSetConverter extends JsonTypeReferenceConverter<EnumSet<UserStatus>> {

  private TypeReference<EnumSet<UserStatus>> typeReference;

  {
    typeReference = new TypeReference<EnumSet<UserStatus>>() {};
  }

  @Override
  protected TypeReference<EnumSet<UserStatus>> typeReference() {
    return typeReference;
  }
}

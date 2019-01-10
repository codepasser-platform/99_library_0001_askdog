package com.stock.capital.common.model.entity.converter;

import static com.stock.capital.common.utils.Json.readValue;
import static com.stock.capital.common.utils.Json.writeValueAsString;

import com.fasterxml.jackson.core.type.TypeReference;
import javax.persistence.AttributeConverter;

/**
 * [JsonTypeReferenceConverter].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public abstract class JsonTypeReferenceConverter<T> implements AttributeConverter<T, String> {

  @Override
  public String convertToDatabaseColumn(T value) {
    return writeValueAsString(value, typeReference());
  }

  @Override
  public T convertToEntityAttribute(String content) {
    return content == null ? null : readValue(content, typeReference());
  }

  protected abstract TypeReference<T> typeReference();
}

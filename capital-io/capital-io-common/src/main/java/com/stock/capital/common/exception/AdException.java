package com.stock.capital.common.exception;

public interface AdException {
  String getCodeValue();

  String getMessage();

  String getLocalizedMessage();

  <C extends Enum<C>> C getCode();
}

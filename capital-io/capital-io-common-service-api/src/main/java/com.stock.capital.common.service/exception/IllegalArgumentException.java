package com.stock.capital.common.service.exception;

import com.stock.capital.common.exception.Message;

/**
 * [IllegalArgumentException].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class IllegalArgumentException extends ServiceRuntimeException {

  private static final long serialVersionUID = -6299563608663320053L;

  public enum Error {
    INVALID_IDENTIFYING_CODE
  }

  public IllegalArgumentException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public IllegalArgumentException(Error error) {
    super(error);
  }

  @Override
  protected String reasonCode() {
    return "ARG";
  }
}

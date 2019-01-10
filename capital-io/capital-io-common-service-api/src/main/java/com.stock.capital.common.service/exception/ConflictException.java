package com.stock.capital.common.service.exception;

import com.stock.capital.common.exception.Message;

/**
 * [ConflictException].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class ConflictException extends ServiceRuntimeException {

  private static final long serialVersionUID = 3837761502401319670L;

  public enum Error {
    USER_USERNAME,
    USER_PHONE
  }

  public ConflictException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public ConflictException(Error error) {
    super(error);
  }

  @Override
  protected String reasonCode() {
    return "CONFLICT";
  }
}

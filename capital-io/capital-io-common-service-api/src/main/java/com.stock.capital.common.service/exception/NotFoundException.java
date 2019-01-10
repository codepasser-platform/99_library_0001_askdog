package com.stock.capital.common.service.exception;

import com.stock.capital.common.exception.Message;

/**
 * [NotFoundException].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class NotFoundException extends ServiceRuntimeException {

  private static final long serialVersionUID = -2909833311527921881L;

  public enum Error {
    USER
  }

  public NotFoundException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public NotFoundException(Error error) {
    super(error);
  }

  @Override
  protected String reasonCode() {
    return "NOT_FOUND";
  }
}

package com.stock.capital.common.service.exception;

import com.stock.capital.common.exception.Message;

/**
 * [ForbiddenException].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class ForbiddenException extends ServiceRuntimeException {

  private static final long serialVersionUID = 2399610140036369185L;

  public enum Error {
    PERMISSIONS
  }

  public ForbiddenException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public ForbiddenException(Error error) {
    super(error);
  }

  @Override
  protected String reasonCode() {
    return "FORBIDDEN";
  }
}

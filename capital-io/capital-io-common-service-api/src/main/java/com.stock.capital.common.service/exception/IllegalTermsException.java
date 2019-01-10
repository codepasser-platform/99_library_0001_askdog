package com.stock.capital.common.service.exception;

import com.stock.capital.common.exception.Message;

/**
 * [IllegalTermsException].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class IllegalTermsException extends ServiceRuntimeException {

  public enum Error {
    IDENTIFYING_CODE
  }

  public IllegalTermsException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public IllegalTermsException(Error error) {
    super(error);
  }

  @Override
  protected String reasonCode() {
    return "TERMS";
  }
}

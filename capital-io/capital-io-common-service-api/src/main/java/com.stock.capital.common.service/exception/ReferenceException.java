package com.stock.capital.common.service.exception;

import com.stock.capital.common.exception.Message;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

/**
 * [ReferenceException].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class ReferenceException extends ServiceRuntimeException {

  private static final long serialVersionUID = -1196230020798256751L;

  public enum Error {
    FAILED
  }

  public ReferenceException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public ReferenceException(JpaObjectRetrievalFailureException e) {
    super(Error.FAILED, e);
  }

  public ReferenceException(Error error) {
    super(error);
  }

  // WEB_VALIDATE_REFERENCE_FAILED
  @Override
  protected String reasonCode() {
    return "REFERENCE";
  }
}

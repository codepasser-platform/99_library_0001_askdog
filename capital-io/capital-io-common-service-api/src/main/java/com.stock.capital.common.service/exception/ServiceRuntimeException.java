package com.stock.capital.common.service.exception;

import static java.lang.String.format;

import com.stock.capital.common.exception.AbstractRuntimeException;
import com.stock.capital.common.exception.Message;

/**
 * [ServiceRuntimeException].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public abstract class ServiceRuntimeException extends AbstractRuntimeException {

  private static final long serialVersionUID = -6045759006673606027L;

  public ServiceRuntimeException(Message message) {
    super(message);
  }

  public ServiceRuntimeException(Enum<?> code) {
    super(code);
  }

  public ServiceRuntimeException(Enum<?> code, Throwable cause) {
    super(code, cause);
  }

  @Override
  protected final String messageResourceBaseName() {
    return "exception.service";
  }

  @Override
  protected final String moduleName() {
    return format("SRV_%s", reasonCode());
  }

  protected abstract String reasonCode();
}

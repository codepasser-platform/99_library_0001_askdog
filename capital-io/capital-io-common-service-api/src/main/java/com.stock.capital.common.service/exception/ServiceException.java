package com.stock.capital.common.service.exception;

import static java.lang.String.format;

import com.stock.capital.common.exception.AbstractException;
import com.stock.capital.common.exception.Message;

/**
 * [ServiceException].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public abstract class ServiceException extends AbstractException {

  private static final long serialVersionUID = -6045759006673606027L;

  public ServiceException(Message message) {
    super(message);
  }

  public ServiceException(Enum<?> code) {
    super(code);
  }

  public ServiceException(Enum<?> code, Throwable cause) {
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

package com.stock.capital.common.service.exception;

import static com.stock.capital.common.service.exception.BadRequestException.Error.FAILED;

import com.stock.capital.common.exception.AbstractRuntimeException;
import com.stock.capital.common.exception.Message;
import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * [BadRequestException].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class BadRequestException extends AbstractRuntimeException {

  private static final long serialVersionUID = 5388350327248338539L;

  public enum Error {
    FAILED
  }

  public BadRequestException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public BadRequestException(Error error) {
    super(error);
  }

  public BadRequestException(IllegalStateException exception) {
    super(FAILED, exception.getMessage());
  }

  public BadRequestException(HttpMessageNotReadableException exception) {
    super(FAILED, exception.getMessage());
  }

  @Override
  protected String messageResourceBaseName() {
    return "exception.service";
  }

  @Override
  protected String moduleName() {
    return "SRV_BAD_REQ";
  }
}

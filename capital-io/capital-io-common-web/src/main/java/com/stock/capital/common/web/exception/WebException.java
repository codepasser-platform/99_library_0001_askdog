package com.stock.capital.common.web.exception;

import static com.stock.capital.common.web.exception.WebException.Error.RUNTIME_ERROR;

import com.stock.capital.common.exception.AbstractException;

/**
 * [WebException].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class WebException extends AbstractException {

  private static final long serialVersionUID = -460735736684833105L;

  public enum Error {
    RUNTIME_ERROR
  }

  public WebException(Error error, String message) {
    super(error, message);
  }

  public WebException(Error error, Throwable cause) {
    super(error, cause);
  }

  public WebException(Throwable cause) {
    super(RUNTIME_ERROR, cause);
  }

  public WebException(RuntimeException exception) {
    super(RUNTIME_ERROR, exception);
  }

  @Override
  protected String messageResourceBaseName() {
    return "exception.web";
  }

  @Override
  protected String moduleName() {
    return "WEB";
  }
}

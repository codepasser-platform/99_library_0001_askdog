package com.stock.capital.common.exception;

import java.io.Serializable;

/**
 * [RepresentationMessage].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class RepresentationMessage implements Serializable {

  private static final long serialVersionUID = -6386495208759109800L;
  private Message message;

  public RepresentationMessage(AdException exception) {
    this.message = new Message(exception);
  }

  public String getCode() {
    return message.getCode();
  }

  public String getMessage() {
    return message.getMessage();
  }

  public String getDetail() {
    return message.getDetail();
  }

  /* TODO Spring security
   public static RepresentationMessage from(AuthenticationException exception) {
    Throwable cause = exception.getCause();
    if (cause instanceof AbstractException) {
      return new RepresentationMessage((AbstractException) cause);
    }

    if (cause instanceof AbstractRuntimeException) {
      return new RepresentationMessage((AbstractRuntimeException) cause);
    }

    return new RepresentationMessage(new AccessException(exception));
  }*/
}

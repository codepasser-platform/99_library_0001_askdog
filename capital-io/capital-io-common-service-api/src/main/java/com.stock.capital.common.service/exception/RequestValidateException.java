package com.stock.capital.common.service.exception;

import static com.stock.capital.common.service.exception.RequestValidateException.Error.FAILED;

import com.stock.capital.common.exception.AbstractRuntimeException;
import com.stock.capital.common.exception.Message;
import java.util.Iterator;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * [RequestValidateException].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class RequestValidateException extends AbstractRuntimeException {

  private static final long serialVersionUID = 5388350327248338539L;

  private String detailMessage;

  public enum Error {
    FAILED
  }

  public RequestValidateException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
    this.detailMessage = message.getMessage();
  }

  public RequestValidateException(MethodArgumentNotValidException exception) {
    setCode(FAILED);
    BindingResult bindingResult = exception.getBindingResult();
    FieldError firstFieldError = bindingResult.getFieldError();
    if (firstFieldError != null) {
      updateCodeMessage(firstFieldError);
    } else {
      Iterator<ObjectError> errors = bindingResult.getAllErrors().iterator();
      if (errors.hasNext()) {
        ObjectError objectError = errors.next();
        updateCodeMessage(objectError);
      }
    }
  }

  public RequestValidateException(java.lang.IllegalArgumentException exception) {
    super(FAILED, exception.getMessage());
    this.detailMessage = exception.getMessage();
  }

  @Override
  public String getMessage() {
    return detailMessage;
  }

  @Override
  protected String messageResourceBaseName() {
    return "exception.service";
  }

  @Override
  protected String moduleName() {
    return "SRV_VALIDATE";
  }

  private void updateCodeMessage(ObjectError objectError) {
    String[] codes = objectError.getCodes();
    if (codes != null && codes.length > 0) {
      this.detailMessage = codes[0] + ": " + objectError.getDefaultMessage();
    }
  }
}

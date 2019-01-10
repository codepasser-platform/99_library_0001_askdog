package com.stock.capital.common.service.advice;

import static com.stock.capital.common.utils.RequestUtils.getArguments;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

import com.stock.capital.common.exception.AbstractException;
import com.stock.capital.common.exception.AbstractRuntimeException;
import com.stock.capital.common.exception.Message;
import com.stock.capital.common.exception.WrappedRuntimeException;
import com.stock.capital.common.processor.annotation.InjectLogger;
import com.stock.capital.common.service.exception.BadRequestException;
import com.stock.capital.common.service.exception.ConflictException;
import com.stock.capital.common.service.exception.ForbiddenException;
import com.stock.capital.common.service.exception.IllegalArgumentException;
import com.stock.capital.common.service.exception.IllegalTermsException;
import com.stock.capital.common.service.exception.NotFoundException;
import com.stock.capital.common.service.exception.ReferenceException;
import com.stock.capital.common.service.exception.RequestValidateException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * [UncaughtExceptionsControllerAdvice].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@ControllerAdvice(annotations = {RestController.class})
public class UncaughtExceptionsControllerAdvice {

  @InjectLogger private static Logger logger;

  /**
   * 捕获自定义运行时异常.
   *
   * @param exception 自定义运行时异常
   * @return message
   */
  @ExceptionHandler(AbstractRuntimeException.class)
  @ResponseBody
  public ResponseEntity<Message> serviceException(
      HttpServletRequest request, AbstractRuntimeException exception) {

    HttpStatus status = BAD_REQUEST;
    if (exception instanceof ConflictException) {
      status = CONFLICT;
    } else if (exception instanceof ForbiddenException) {
      status = FORBIDDEN;
    } else if (exception instanceof IllegalArgumentException) {
      status = BAD_REQUEST;
    } else if (exception instanceof IllegalTermsException) {
      status = BAD_REQUEST;
    } else if (exception instanceof NotFoundException) {
      status = NOT_FOUND;
    } else if (exception instanceof ReferenceException) {
      status = UNPROCESSABLE_ENTITY;
    }
    Message message = new Message(exception);
    warnLog(request, message);
    return ResponseEntity.status(status).contentType(APPLICATION_JSON_UTF8).body(message);
  }

  /**
   * 捕获数据关系错误异常.
   *
   * @param exception 数据关系错误异常
   * @return message
   */
  @ExceptionHandler(JpaObjectRetrievalFailureException.class)
  @ResponseBody
  @ResponseStatus(UNPROCESSABLE_ENTITY)
  public Message handleArgumentErrors(
      HttpServletRequest request, JpaObjectRetrievalFailureException exception) {
    Message message = new Message(new ReferenceException(exception));
    warnLog(request, message);
    return message;
  }

  /**
   * 捕获其他未知异常.
   *
   * @param exception 系统其他未知异常
   * @return message
   */
  @ExceptionHandler(AbstractException.class)
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  public Message serviceException(HttpServletRequest request, AbstractException exception) {
    Message message = new Message(exception);
    warnLog(request, message);
    return message;
  }

  /**
   * 捕获系统运行时异常.
   *
   * @param exception 系统运行时异常
   * @return message
   */
  @ExceptionHandler(RuntimeException.class)
  @ResponseBody
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public Message runtimeException(HttpServletRequest request, RuntimeException exception) {
    exception.printStackTrace();
    Message message = new Message(new WrappedRuntimeException(exception));
    errorLog(request, message);
    return message;
  }

  /**
   * IllegalStateException handling.
   *
   * @param exception IllegalStateException
   * @return message
   */
  @ExceptionHandler(IllegalStateException.class)
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  public Message handleArgumentErrors(HttpServletRequest request, IllegalStateException exception) {
    Message message = new Message(new BadRequestException(exception));
    warnLog(request, message);
    return message;
  }

  /**
   * HttpMessageNotReadableException handling.
   *
   * @param exception HttpMessageNotReadableException
   * @return message
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  public Message handleArgumentErrors(
      HttpServletRequest request, HttpMessageNotReadableException exception) {
    Message message = new Message(new BadRequestException(exception));
    warnLog(request, message);
    return message;
  }

  /**
   * MethodArgumentNotValidException handling.
   *
   * @param exception MethodArgumentNotValidException
   * @return message
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  public Message handleArgumentErrors(
      HttpServletRequest request, MethodArgumentNotValidException exception) {
    Message message = new Message(new RequestValidateException(exception));
    warnLog(request, message);
    return message;
  }

  /**
   * IllegalArgumentException handling.
   *
   * @param exception IllegalArgumentException
   * @return message
   */
  @ExceptionHandler(java.lang.IllegalArgumentException.class)
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  public Message handleArgumentErrors(
      HttpServletRequest request, java.lang.IllegalArgumentException exception) {
    Message message = new Message(new RequestValidateException(exception));
    warnLog(request, message);
    return message;
  }

  private void warnLog(HttpServletRequest request, Message message) {
    logger.warn(
        "Process request url '{}', arguments=[{}], errorCode=[{}], errorMsg=[{}], errorDetail=[{}]",
        request.getRequestURL(),
        getArguments(request),
        message.getCode(),
        message.getMessage(),
        message.getDetail());
  }

  private void errorLog(HttpServletRequest request, Message message) {
    logger.error(
        "Process request url '{}', arguments=[{}], errorCode=[{}], errorMsg=[{}], errorDetail=[{}]",
        request.getRequestURL(),
        getArguments(request),
        message.getCode(),
        message.getMessage(),
        message.getDetail());
  }
}

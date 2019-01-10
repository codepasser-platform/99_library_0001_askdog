package com.askdog.store.web.api.advice;

import com.askdog.common.exception.Message;
import com.askdog.store.service.exception.ConflictException;
import com.askdog.store.service.exception.ForbiddenException;
import com.askdog.store.service.exception.NotFoundException;
import com.askdog.store.service.exception.ServiceException;
import com.askdog.store.web.api.exception.ReferenceException;
import com.askdog.store.web.api.exception.RequestValidateException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice(annotations = {RestController.class})
public class UncaughtExceptionsControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    public Message handleArgumentErrors(MethodArgumentNotValidException exception) {
        return new Message(new RequestValidateException(exception));
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    public Message handleArgumentErrors(JpaObjectRetrievalFailureException exception) {
        return new Message(new ReferenceException(exception));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    public Message handleArgumentErrors(IllegalArgumentException exception) {
        return new Message(new RequestValidateException(exception));
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    public Message serviceException(ServiceException exception) {
        return new Message(exception);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseBody
    @ResponseStatus(CONFLICT)
    public Message conflictException(ConflictException exception) {
        return new Message(exception);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    public Message notFoundException(NotFoundException exception) {
        return new Message(exception);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    @ResponseStatus(FORBIDDEN)
    public Message forbiddenException(ForbiddenException exception) {
        return new Message(exception);
    }

}
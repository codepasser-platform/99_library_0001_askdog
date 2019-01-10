package com.askdog.service.utils;

import com.askdog.service.exception.ServiceException;

public interface ConvertFunction<T, R> {

    R apply(T t) throws ServiceException;
}

package com.askdog.store.service.util;


import com.askdog.store.service.exception.ServiceException;

public interface ConvertFunction<T, R> {

    R apply(T t) throws ServiceException;

}

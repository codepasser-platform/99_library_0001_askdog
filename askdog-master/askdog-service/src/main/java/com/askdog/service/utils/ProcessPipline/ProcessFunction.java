package com.askdog.service.utils.ProcessPipline;

import com.askdog.service.exception.ServiceException;

public interface ProcessFunction<T, R> {
        R process(T intermediateValue) throws ServiceException;
    }
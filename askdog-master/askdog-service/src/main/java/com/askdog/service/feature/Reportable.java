package com.askdog.service.feature;

import com.askdog.service.bo.PureReport;
import com.askdog.service.exception.ServiceException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Reportable {
    void report(@Nullable String userId, @Nonnull String target, @Nonnull PureReport pureReport) throws ServiceException;
}

package com.askdog.service;

import com.askdog.model.data.OriginalNotification;
import com.askdog.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nonnull;
import java.util.List;

public interface NotificationService {

    @Nonnull
    Page<OriginalNotification> findAll(@Nonnull String userId, @Nonnull Pageable pageable) throws ServiceException;

    @Nonnull
    List<OriginalNotification> findPreviewNotifications(@Nonnull String userId) throws ServiceException;

    void markAsRead(@Nonnull String notificationId) throws ServiceException;

    long findNoticeCount(@Nonnull String userId);

    void readAll(@Nonnull String userId);
}

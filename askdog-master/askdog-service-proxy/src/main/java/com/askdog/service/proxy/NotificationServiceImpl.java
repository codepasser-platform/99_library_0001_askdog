package com.askdog.service.proxy;

import com.askdog.model.data.OriginalNotification;
import com.askdog.service.NotificationService;
import com.askdog.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Nonnull
    @Override
    public Page<OriginalNotification> findAll(@Nonnull String userId, @Nonnull Pageable pageable) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public List<OriginalNotification> findPreviewNotifications(@Nonnull String userId) throws ServiceException {
        return null;
    }

    @Override
    public void markAsRead(@Nonnull String notificationId) throws ServiceException {

    }

    @Override
    public long findNoticeCount(@Nonnull String userId) {
        return 0;
    }

    @Override
    public void readAll(@Nonnull String userId) {

    }
}

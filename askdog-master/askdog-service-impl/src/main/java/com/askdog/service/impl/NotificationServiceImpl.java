package com.askdog.service.impl;

import com.askdog.model.data.OriginalNotification;
import com.askdog.model.repository.mongo.OriginalNotificationRepository;
import com.askdog.service.NotificationService;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

import java.util.List;

import static com.askdog.service.exception.NotFoundException.Error.NOTIFICATION;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Autowired private OriginalNotificationRepository originalNotificationRepository;

    @Nonnull
    @Override
    public Page<OriginalNotification> findAll(@Nonnull String recipient, @Nonnull Pageable pageable) throws ServiceException {
        Page<OriginalNotification> notifications = originalNotificationRepository.findByRecipient(recipient, pageable);
        for (OriginalNotification originalNotification : notifications) {
            markAsRead(originalNotification.getId());
            originalNotification.setRead(true);
        }

        return notifications;
    }

    @Nonnull
    @Override
    public List<OriginalNotification> findPreviewNotifications(@Nonnull String userId) throws ServiceException {
        return originalNotificationRepository.findTop10ByRecipientAndReadOrderByCreateTimeDesc(userId, false);
    }

    @Override
    public void markAsRead(@Nonnull String notificationId) throws ServiceException {
        OriginalNotification originalNotification = originalNotificationRepository.findById(notificationId).orElseThrow(() -> new NotFoundException(NOTIFICATION));
        originalNotification.setRead(true);
        originalNotificationRepository.save(originalNotification);
    }

    @Override
    public long findNoticeCount(@Nonnull String userId) {
        return originalNotificationRepository.countByRecipientAndReadFalse(userId);
    }

    @Override
    public void readAll(@Nonnull String userId) {
        originalNotificationRepository.readAll(userId);
    }
}

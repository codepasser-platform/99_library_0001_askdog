package com.askdog.model.repository.mongo;

import com.askdog.model.data.OriginalNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OriginalNotificationRepository extends MongoRepository<OriginalNotification, String>, OriginalNotificationRepositoryExtention {

    Optional<OriginalNotification> findById(String notificationId);

    Page<OriginalNotification> findByRecipient(String recipient, Pageable pageable);

    List<OriginalNotification> findTop10ByRecipientAndReadOrderByCreateTimeDesc(String recipient, boolean read);

    long countByRecipientAndReadFalse(String recipient);

}

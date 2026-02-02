package com.cts.elearn.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.elearn.entity.Notification;
import com.cts.elearn.entity.Notification.NotificationType;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    Optional<Notification> findByRecipientIdAndNotificationType(
            int recipientId,
            NotificationType notificationType
    );
}

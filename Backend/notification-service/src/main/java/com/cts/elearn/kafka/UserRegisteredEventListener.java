package com.cts.elearn.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cts.elearn.dto.UserRegisteredEvent;
import com.cts.elearn.entity.Notification;
import com.cts.elearn.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserRegisteredEventListener {

    private final NotificationService notificationService;

    public UserRegisteredEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
        topics = "user.registered",
        groupId = "notification-service",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleUserRegistered(UserRegisteredEvent event) {

        log.info("📩 USER REGISTERED EVENT RECEIVED: {}", event);

        notificationService.handleUserRegistration(event);
    }
}

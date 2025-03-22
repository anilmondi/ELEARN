package com.cts.elearn.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.elearn.client.UserClient;
import com.cts.elearn.dao.NotificationRepository;
import com.cts.elearn.dto.UserResponse;
import com.cts.elearn.entity.Notification;
import com.cts.elearn.entity.Notification.NotificationStatus;
import com.cts.elearn.entity.Notification.NotificationType;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;
	/*
	 * @Autowired private SmsService smsService; // New SMS service
	 */
    @Autowired
    private UserClient userClient;  // Fetch email & phone number from `user-service`

    public Notification createNotification(Notification notification) {
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());

        // Fetch user details from `user-service`
        UserResponse user = userClient.getUserById(notification.getRecipientId());

        // 🔹 Debug: Print user details
        System.out.println("🔹 Retrieved User Details: " + user);
        System.out.println("🔹 User Contact Number: [" + user.getContactNumber() + "]");

        if (user != null) {
            String email = user.getEmail();
            String phoneNumber = user.getContactNumber();

            // ✅ Ensure the user has contact number or email
            if (notification.getNotificationType() == NotificationType.SMS) {
                if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                    throw new RuntimeException("❌ User does not have a registered mobile number.");
                }
            }
            // ✅ Send Email Notification
            if (notification.getNotificationType() == NotificationType.EMAIL) {
                String subject = "New Notification from Elearn";
                String body = "Dear " + user.getName() + ",\n\n" + notification.getNotificationMessage() + "\n\nBest Regards,\nElearn Team";
                emailService.sendEmail(email, subject, body);
                notification.setStatus(NotificationStatus.SENT);
                notification.setSentAt(LocalDateTime.now());
            }
        } else {
            throw new RuntimeException("❌ User not found in user-service!");
        }

        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(int id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));
    }

    public void updateNotification(Notification notification) {
        Notification existingNotification = notificationRepository.findById(notification.getNotificationId())
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notification.getNotificationId()));

        existingNotification.setNotificationMessage(notification.getNotificationMessage());
        existingNotification.setRecipientId(notification.getRecipientId());
        existingNotification.setNotificationType(notification.getNotificationType());
        existingNotification.setStatus(notification.getStatus());
        existingNotification.setSentAt(notification.getSentAt());

        notificationRepository.save(existingNotification);
    }

    public void deleteNotification(int id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification not found with ID: " + id);
        }
        notificationRepository.deleteById(id);
    }
}

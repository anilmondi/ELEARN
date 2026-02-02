package com.cts.elearn.domain.event;

import java.time.Instant;

public class PasswordResetRequestedEvent implements DomainEvent {

    private final Long userId;
    private final String email;
    private final String resetToken;
    private final Instant occurredAt = Instant.now();

    public PasswordResetRequestedEvent(Long userId, String email, String resetToken) {
        this.userId = userId;
        this.email = email;
        this.resetToken = resetToken;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getResetToken() {
        return resetToken;
    }

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }
}

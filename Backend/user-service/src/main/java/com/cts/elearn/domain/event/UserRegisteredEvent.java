package com.cts.elearn.domain.event;

import java.time.Instant;

public class UserRegisteredEvent implements DomainEvent {

    private final Long userId;
    private final String email;
    private final Instant occurredAt = Instant.now();

    public UserRegisteredEvent(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }
}

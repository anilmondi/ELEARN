package com.cts.elearn.domain.event;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredAt();
}

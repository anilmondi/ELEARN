package com.cts.elearn.event;

import com.cts.elearn.domain.event.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}

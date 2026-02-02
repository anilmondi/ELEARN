package com.cts.elearn.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.cts.elearn.domain.event.DomainEvent;
import com.cts.elearn.domain.event.UserRegisteredEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaDomainEventPublisher implements DomainEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaDomainEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(DomainEvent event) {

        if (event instanceof UserRegisteredEvent e) {
            log.info("🔥 Publishing USER REGISTERED event to Kafka: {}", e);

            kafkaTemplate
                .send("user.registered", e.getEmail(), e)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("❌ Kafka publish failed", ex);
                    } else {
                        log.info(
                            "✅ Kafka publish success | topic={}, partition={}, offset={}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset()
                        );
                    }
                });
        }
    }
}

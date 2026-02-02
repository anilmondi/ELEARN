package com.cts.elearn.kafka;

import com.cts.elearn.dao.PurchaseHistoryRepository;
import com.cts.elearn.entity.PurchaseHistory;
import com.cts.elearn.event.CoursePurchasedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CoursePurchasedListener {

    private final PurchaseHistoryRepository repository;

    public CoursePurchasedListener(PurchaseHistoryRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
        topics = "course.purchased",
        groupId = "purchasehistory-service",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handlePurchase(CoursePurchasedEvent event) {

        // 🔐 Idempotency check
        if (repository.existsByEventId(event.getEventId())) {
            log.warn("⚠️ Duplicate Kafka event ignored | eventId={}", event.getEventId());
            return;
        }

        PurchaseHistory ph = new PurchaseHistory();
        ph.setEventId(event.getEventId());
        ph.setLearnerId(event.getLearnerId());
        ph.setCourseId(event.getCourseId());
        ph.setServiceId(event.getServiceId());

        repository.save(ph);

        log.info(
            "💰 Purchase stored | eventId={}, learnerId={}, courseId={}",
            event.getEventId(),
            event.getLearnerId(),
            event.getCourseId()
        );
    }
}

package com.cts.elearn.event;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePurchasedEvent {

    private String eventId;
    private Long learnerId;
    private Long courseId;
    private Long serviceId;

    public static CoursePurchasedEvent of(Long learnerId, Long courseId, Long serviceId) {
        return new CoursePurchasedEvent(
            UUID.randomUUID().toString(),
            learnerId,
            courseId,
            serviceId
        );
    }
}

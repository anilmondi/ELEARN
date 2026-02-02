package com.cts.elearn.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreatedEvent {
	private String eventId;
    private Long courseId;
    private int rating;
}

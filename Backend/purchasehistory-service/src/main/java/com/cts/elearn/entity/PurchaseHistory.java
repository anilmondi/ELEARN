package com.cts.elearn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchasehistory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    @Column(name = "learner_id", nullable = false)
    private Long learnerId;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;			//full access or semi access 

    @Column(name = "purchase_date", nullable = false, updatable = false)
    private LocalDateTime purchaseDate;
    
    @Column(name = "event_id", nullable = false, unique = true)
    private String eventId;


    @PrePersist
    protected void onCreate() {
        this.purchaseDate = LocalDateTime.now();
    }
}

package com.cts.elearn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    private Long courseId;

    private Long learnerId;

    @NotBlank(message = "Review text cannot be empty")
    private String reviewText;

    @Min(1)
    @Max(5)
    private int rating;

    private LocalDateTime createdAt;
}

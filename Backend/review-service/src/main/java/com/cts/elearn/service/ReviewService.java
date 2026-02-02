package com.cts.elearn.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.cts.elearn.entity.Review;
import com.cts.elearn.event.ReviewCreatedEvent;
import com.cts.elearn.exception.AlreadyReviewedException;
import com.cts.elearn.exception.CourseNotPurchasedException;
import com.cts.elearn.feign.PurchaseHistoryClient;
import com.cts.elearn.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PurchaseHistoryClient purchaseHistoryClient;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    
    public Review createReview(Review review) {

        // 1️⃣ Check duplicate review (STEP-1)
        reviewRepository
            .findByCourseIdAndLearnerId(
                review.getCourseId(),
                review.getLearnerId()
            )
            .ifPresent(r -> {
                throw new AlreadyReviewedException(
                    "You already reviewed this course"
                );
            });

        // 2️⃣ Check purchase
        boolean purchased = purchaseHistoryClient
            .getPurchasesByLearner((long) review.getLearnerId())
            .stream()
            .anyMatch(p -> p.getCourseId().equals(review.getCourseId()));

        if (!purchased) {
            throw new CourseNotPurchasedException(
                "Purchase course before reviewing"
            );
        }

        review.setCreatedAt(LocalDateTime.now());
        
        Review saved = reviewRepository.save(review);

        ReviewCreatedEvent event =
                new ReviewCreatedEvent(
                	UUID.randomUUID().toString(),	
                    saved.getCourseId(),
                    saved.getRating()
                );

        kafkaTemplate.send(
            "review.created",
            saved.getCourseId().toString(),
            event
        );

        return saved;

    }

    
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(int reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with ID: " + reviewId));
    }

    public List<Review> getReviewsByCourseId(Long courseId) {
        return reviewRepository.findByCourseId(courseId);
    }

    public Review updateReview(int reviewId, Review updatedReview) {
        Review review = getReviewById(reviewId);
        review.setReviewText(updatedReview.getReviewText());
        review.setRating(updatedReview.getRating());
        return reviewRepository.save(review);
    }

    public void deleteReview(int reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}

package com.cts.elearn.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.elearn.entity.Review;
import com.cts.elearn.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(int reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with ID: " + reviewId));
    }

    public List<Review> getReviewsByCourseId(int courseId) {
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

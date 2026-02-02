package com.cts.elearn.controller;

import com.cts.elearn.entity.Review;
import com.cts.elearn.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Review addReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable int id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping("/course/{courseId}")
    public List<Review> getReviewsByCourse(@PathVariable Long courseId) {
        return reviewService.getReviewsByCourseId(courseId);
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable int id, @RequestBody Review review) {
        return reviewService.updateReview(id, review);
    }

    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
        return "Review deleted successfully!";
    }
}

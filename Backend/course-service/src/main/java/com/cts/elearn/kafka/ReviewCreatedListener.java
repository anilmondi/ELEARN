package com.cts.elearn.kafka;

import com.cts.elearn.event.ReviewCreatedEvent;
import com.cts.elearn.entity.Course;
import com.cts.elearn.dao.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class ReviewCreatedListener {

    private final CourseRepository courseRepository;

    public ReviewCreatedListener(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @KafkaListener(
        topics = "review.created",
        groupId = "course-service"
    )
    public void handleReviewCreated(ReviewCreatedEvent event) {

        Course course = courseRepository.findById(event.getCourseId())
            .orElseThrow(() -> new RuntimeException("Course not found"));

      
        int count = course.getReviewCount() == null ? 0 : course.getReviewCount();
        BigDecimal avg = course.getAvgRating() == null ? BigDecimal.ZERO : course.getAvgRating();


        BigDecimal newAvg =
            avg.multiply(BigDecimal.valueOf(count))
               .add(BigDecimal.valueOf(event.getRating()))
               .divide(BigDecimal.valueOf(count + 1), 2, BigDecimal.ROUND_HALF_UP);

        course.setReviewCount(count + 1);
        course.setAvgRating(newAvg);

        courseRepository.save(course);

        log.info("⭐ Rating updated | courseId={}, avg={}, count={}",
                course.getCourseId(),
                course.getAvgRating(),
                course.getReviewCount());
    }
}

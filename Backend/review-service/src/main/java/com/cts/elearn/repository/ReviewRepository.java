package com.cts.elearn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.elearn.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    
    boolean existsByCourseIdAndLearnerId(Long courseId, Long learnerId);

    List<Review> findByCourseId(Long courseId);
    
    Optional<Review> findByCourseIdAndLearnerId(Long courseId, Long learnerId);


}

package com.cts.elearn.dao;

import com.cts.elearn.entity.Course;
import com.cts.elearn.entity.Course.Category;
import com.cts.elearn.entity.Course.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByVendorId(Long vendorId);

    List<Course> findByCategory(Category category);

    List<Course> findByVendorIdAndCategory(Long vendorId, Category category);

    List<Course> findByStatus(CourseStatus status);
}

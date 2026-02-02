package com.cts.elearn.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.elearn.dao.CourseRepository;
import com.cts.elearn.entity.Course;
import com.cts.elearn.entity.Course.Category;
import com.cts.elearn.entity.Course.CourseStatus;
import com.cts.elearn.event.CoursePurchasedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> searchCourses(Long vendorId, Category category) {
        if (vendorId != null && category != null) {
            return courseRepository.findByVendorIdAndCategory(vendorId, category);
        } else if (vendorId != null) {
            return courseRepository.findByVendorId(vendorId);
        } else if (category != null) {
            return courseRepository.findByCategory(category);
        }
        return courseRepository.findAll();
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Course course = getCourseById(id);
        course.setCourseName(updatedCourse.getCourseName());
        course.setDescription(updatedCourse.getDescription());
        course.setCategory(updatedCourse.getCategory());
        course.setVendorId(updatedCourse.getVendorId());
        course.setStatus(updatedCourse.getStatus());
        course.setPrice(updatedCourse.getPrice());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        course.setStatus(CourseStatus.INACTIVE); // Soft delete
        courseRepository.save(course);
    }
    
    public void purchaseCourse(Long learnerId, Long courseId) {

        CoursePurchasedEvent event =
                CoursePurchasedEvent.of(learnerId, courseId, 1L);

        kafkaTemplate.send(
            "course.purchased",
            learnerId.toString(),
            event
        );

        log.info("📦 COURSE PURCHASE EVENT SENT | eventId={}, learnerId={}, courseId={}",
                event.getEventId(), learnerId, courseId);
    }


}

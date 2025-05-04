package com.cts.elearn.feign;

import com.cts.elearn.dto.CourseResponse;  // Create a DTO for Course response
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service", url = "http://localhost:8082")  // Make sure to use correct URL for course service
public interface CourseClient {
    @GetMapping("/courses/{courseId}")
    CourseResponse getCourseById(@PathVariable("courseId") int courseId);
}

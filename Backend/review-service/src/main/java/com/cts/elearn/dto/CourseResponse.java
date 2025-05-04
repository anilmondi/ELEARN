package com.cts.elearn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponse {
    private int courseId;
    private String courseName;
    private String description;
    private String category;
    private String status;
}

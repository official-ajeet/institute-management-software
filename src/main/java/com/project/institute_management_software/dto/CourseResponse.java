package com.project.institute_management_software.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.institute_management_software.entities.Course;
import com.project.institute_management_software.entities.Instructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseResponse {
    private int courseId;
    private String name;
    private String description;
    private String duration;
    private int instructorId;
    private String message;

    public CourseResponse(String name, String description, String duration, int instructorId) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.instructorId = instructorId;
    }

    public static CourseResponse toCourseResponse(Course course) {

        return CourseResponse.builder()
                .courseId(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .duration(course.getDuration())
                .instructorId(course.getInstructor() != null ? course.getInstructor().getId() : null) // Check if instructor is null
                .build();
    }
}

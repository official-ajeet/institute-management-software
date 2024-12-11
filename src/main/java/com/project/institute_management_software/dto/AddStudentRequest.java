package com.project.institute_management_software.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.institute_management_software.entities.Course;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddStudentRequest {
    private String name;
    private String email;
    private String mobile;
    private int courseId;
    private LocalDate enrollmentDate;
}

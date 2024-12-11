package com.project.institute_management_software.dto;

import com.project.institute_management_software.entities.Course;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EditStudentRequest {
    private String name;
    private String email;
    private String mobile;
    private LocalDate enrollmentDate;
    private int courseId;
}

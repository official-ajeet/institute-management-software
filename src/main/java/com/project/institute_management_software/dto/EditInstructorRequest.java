package com.project.institute_management_software.dto;

import com.project.institute_management_software.entities.Course;
import lombok.Data;

import java.util.List;

@Data
public class EditInstructorRequest {
    private String name;
    private String email;
    private String specialization;
    private int courseId;
}

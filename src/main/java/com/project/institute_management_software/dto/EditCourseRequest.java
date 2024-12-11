package com.project.institute_management_software.dto;

import com.project.institute_management_software.entities.Instructor;
import lombok.Data;

@Data
public class EditCourseRequest {
    private String name;
    private String description;
    private String duration;
    private int instructorId;
}

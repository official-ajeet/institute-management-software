package com.project.institute_management_software.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.institute_management_software.entities.Course;
import com.project.institute_management_software.entities.Instructor;
import com.project.institute_management_software.entities.Student;
import com.project.institute_management_software.repository.InstructorRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstructorResponse {
    private int instructorId;
    private String name;
    private String email;
    private String specialization;
    private String courseId;
    private String message;

    public InstructorResponse(String name, String email, String specialization) {
        this.name = name;
        this.email = email;
        this.specialization = specialization;
    }

    public static InstructorResponse toInstructorResponse(Instructor instructor){
        return InstructorResponse.builder()
                .instructorId(instructor.getId())
                .name(instructor.getName())
                .email(instructor.getEmail())
                .specialization(instructor.getSpecialization())

                .build();
    }
}

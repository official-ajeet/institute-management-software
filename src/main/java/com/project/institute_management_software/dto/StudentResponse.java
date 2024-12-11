package com.project.institute_management_software.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.institute_management_software.entities.Course;
import com.project.institute_management_software.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse {
    private int studentId;
    private String name;
    private String mobile;
    private String email;
    private LocalDate enrollmentDate;
    private int courseId;
    private String message;

    public StudentResponse(String name, String email, String mobile, LocalDate enrollmentDate, int courseId) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.enrollmentDate = enrollmentDate;
        this.courseId = courseId;
    }


    public static StudentResponse toStudentResponse(Student student) {
        return StudentResponse.builder()
                .studentId(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .mobile(student.getMobile())
                .courseId(student.getCourse() != null? student.getCourse().getId():null)
                .enrollmentDate(student.getEnrollmentDate())
                .build();
    }

}



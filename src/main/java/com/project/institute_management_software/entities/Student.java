package com.project.institute_management_software.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String mobile;
    private String email;
    private LocalDate enrollmentDate;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course; // Many students can enroll in one course

    public Student(String name, String email, String mobile, LocalDate enrollmentDate, Course course) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.enrollmentDate = enrollmentDate;
        this.course = course;
    }

}

package com.project.institute_management_software.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the student

    @NotNull(message = "Name cannot be null")
    private String name; // Full name of the student

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number format")
    private String mobile; // Contact number of the student

    @Email(message = "Invalid email format")
    @Column(unique = true)  // Ensures the email field is unique in the database
    private String email; // Email address of the student

    private LocalDate enrollmentDate; // Date of enrollment in the course

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course; // The course in which the student is enrolled

    // Constructor for creating a student with basic details and course
    public Student(String name, String email, String mobile, LocalDate enrollmentDate, Course course) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.enrollmentDate = enrollmentDate;
        this.course = course;
    }
}


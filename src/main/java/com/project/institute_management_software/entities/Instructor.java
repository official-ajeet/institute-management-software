package com.project.institute_management_software.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Unique identifier for the instructor

    @NotNull(message = "Name cannot be null")
    private String name; // Full name of the instructor

    @Email(message = "Invalid email format")
    private String email; // Email address of the instructor

    private String specialization; // Subject or area of expertise

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<Course> courses; // List of courses taught by this instructor

    // Constructor for instantiating with basic details
    public Instructor(String name, String email, String specialization) {
        this.name = name;
        this.email = email;
        this.specialization = specialization;
    }
}


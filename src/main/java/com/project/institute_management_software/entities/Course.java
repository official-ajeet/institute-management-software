package com.project.institute_management_software.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the course

    private String name; // Name of the course
    private String description; // Description of the course content
    private String duration; // Duration of the course (e.g., weeks or months)

    @ManyToOne
    @JoinColumn(name = "instructorId") // Foreign key for instructor
    private Instructor instructor; // Many courses are taught by one instructor

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Student> students; // One course can have many students enrolled

    // Constructor for easy instantiation with basic details
    public Course(String name, String description, String duration, Instructor instructor) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.instructor = instructor;
    }
}


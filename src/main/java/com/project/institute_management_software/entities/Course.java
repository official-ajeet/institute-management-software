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
    private int id;
    private String name;
    private String description;
    private String duration;

    @ManyToOne
    @JoinColumn(name = "instructorId")
    private Instructor instructor; // Many courses are taught by one instructor
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL   )
    private List<Student> students; // One course can have many students

    public Course(String name, String description, String duration, Instructor instructor) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.instructor = instructor;
    }
}

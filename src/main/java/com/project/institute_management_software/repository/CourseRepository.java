package com.project.institute_management_software.repository;

import com.project.institute_management_software.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findByNameContainingIgnoreCaseOrInstructorNameContainingIgnoreCase(String courseName, String instructorName);
}

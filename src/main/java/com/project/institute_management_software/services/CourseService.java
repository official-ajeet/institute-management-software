package com.project.institute_management_software.services;

import com.project.institute_management_software.dto.AddCourseRequest;
import com.project.institute_management_software.dto.CourseResponse;
import com.project.institute_management_software.dto.EditCourseRequest;
import com.project.institute_management_software.entities.Course;
import com.project.institute_management_software.entities.Instructor;
import com.project.institute_management_software.repository.CourseRepository;
import com.project.institute_management_software.repository.InstructorRepository;
import com.project.institute_management_software.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    // Adds a new course to the system
    public Course addCourse(AddCourseRequest addCourseRequest) {
        Course course = new Course();
        Instructor instructor = instructorRepository.findById(addCourseRequest.getInstructorId()).orElse(null);

        course.setName(addCourseRequest.getName());
        course.setDescription(addCourseRequest.getDescription());
        course.setDuration(addCourseRequest.getDuration());

        // Set instructor if valid
        if (instructor != null) {
            course.setInstructor(instructor);
        }

        return courseRepository.save(course);
    }

    // Updates an existing course by ID
    public Course editCourse(int id, EditCourseRequest editCourseRequest) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            course.setName(editCourseRequest.getName());
            course.setDescription(editCourseRequest.getDescription());
            course.setDuration(editCourseRequest.getDuration());
        }

        return courseRepository.save(course);
    }

    // Retrieves a course by its ID
    public Course getCourseById(int courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    // Retrieves all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Deletes a course by its ID
    public Course deleteCourse(int courseId) {
        Course existingCourse = courseRepository.findById(courseId).orElse(null);
        if (existingCourse != null) {
            courseRepository.deleteById(courseId);
        }
        return existingCourse;
    }

    // Searches for courses by a keyword (course name or instructor name)
    public List<CourseResponse> search(String key) {
        // Retrieve all courses and instructors
        List<Course> courseList = courseRepository.findAll();
        List<Instructor> instructorList = instructorRepository.findAll();

        // Store search results
        List<Course> searchResult = new ArrayList<>();

        // Search by course name or instructor name
        for (Course course : courseList) {
            if (course.getName().toLowerCase().contains(key.toLowerCase())) {
                searchResult.add(course);
            } else {
                for (Instructor instructor : instructorList) {
                    if (course.getInstructor().getName().toLowerCase().contains(key.toLowerCase())) {
                        searchResult.add(course);
                        break; // Match found; move to the next course
                    }
                }
            }
        }

        // Convert search results to CourseResponse DTOs
        return searchResult.stream()
                .map(CourseResponse::toCourseResponse)
                .collect(Collectors.toList());
    }
}


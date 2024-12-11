package com.project.institute_management_software.services;

import com.project.institute_management_software.dto.AddCourseRequest;
import com.project.institute_management_software.dto.CourseResponse;
import com.project.institute_management_software.dto.EditCourseRequest;
import com.project.institute_management_software.entities.Course;
import com.project.institute_management_software.entities.Instructor;
import com.project.institute_management_software.entities.Student;
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

    
    public Course addCourse(AddCourseRequest addCourseRequest){
        Course course = new Course();
        Instructor instructor = instructorRepository.findById(addCourseRequest.getInstructorId()).orElse(null);

        course.setName(addCourseRequest.getName());
        course.setDescription(addCourseRequest.getDescription());
        course.setDuration(addCourseRequest.getDuration());
        if(instructor != null){
            course.setInstructor(instructor);
        }

        return courseRepository.save(course);
    }

    public Course editCourse(int id, EditCourseRequest editCourseRequest){
        Course course = courseRepository.findById(id).orElse(null);
        if(course != null){
            course.setName(editCourseRequest.getName());
            course.setDescription(editCourseRequest.getDescription());
            course.setDuration(editCourseRequest.getDuration());
        }

        return courseRepository.save(course);
    }

    public Course getCourseById(int courseId){
        return courseRepository.findById(courseId).orElse(null);
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course deleteCourse(int courseId){
        Course existingCourse = courseRepository.findById(courseId).orElse(null);
        if(existingCourse != null){
            courseRepository.deleteById(courseId);
        }
        return existingCourse;
    }


    // Method to search courses by course name or instructor name
    public List<CourseResponse> search(String key) {
        // Get all courses and instructors
        List<Course> courseList = courseRepository.findAll();
        List<Instructor> instructorList = instructorRepository.findAll();

        // List to hold the search results
        List<Course> searchResult = new ArrayList<>();

        // Iterate over the courseList to find matches by course name or instructor name
        for (Course course : courseList) {
            // Check if the course name contains the key (case-insensitive)
            if (course.getName().toLowerCase().contains(key.toLowerCase())) {
                searchResult.add(course);
            } else {
                // If the instructor's name contains the key, add the course to the search result
                for (Instructor instructor : instructorList) {
                    if (course.getInstructor().getName().toLowerCase().contains(key.toLowerCase())) {
                        searchResult.add(course);
                        break; // If we found a match, no need to continue checking instructors
                    }
                }
            }
        }

        // Convert the search result list to CourseResponse DTOs
        List<CourseResponse> courseResponses = searchResult.stream()
                .map(CourseResponse::toCourseResponse)
                .collect(Collectors.toList());

        // Return the search results as a list of CourseResponse objects
        return courseResponses;
    }

}



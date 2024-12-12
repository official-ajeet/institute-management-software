package com.project.institute_management_software.controller;

import com.project.institute_management_software.dto.AddCourseRequest;
import com.project.institute_management_software.dto.CourseResponse;
import com.project.institute_management_software.dto.EditCourseRequest;
import com.project.institute_management_software.entities.Course;
import com.project.institute_management_software.repository.CourseRepository;
import com.project.institute_management_software.repository.InstructorRepository;
import com.project.institute_management_software.repository.StudentRepository;
import com.project.institute_management_software.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller to manage course-related operations.
 */
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Adds a new course.
     * @param addCourseRequest the course details.
     * @return the response containing course information and a success message.
     */
    @PostMapping("/add")
    public ResponseEntity<CourseResponse> add(@RequestBody AddCourseRequest addCourseRequest) {
        Course course = courseService.addCourse(addCourseRequest);
        CourseResponse courseResponse = CourseResponse.toCourseResponse(course);
        courseResponse.setMessage("Course Added Successfully!");
        return ResponseEntity.ok(courseResponse);
    }

    /**
     * Retrieves a course by its ID.''
     * @param id the ID of the course.
     * @return the response containing course details or a not-found message.
     */
    @GetMapping("/getById")
    public ResponseEntity<CourseResponse> getById(@RequestParam int id) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            CourseResponse courseResponse = new CourseResponse();
            courseResponse.setMessage("No course found with id: " + id);
            return ResponseEntity.ofNullable(courseResponse);
        }else{
            CourseResponse courseResponse = CourseResponse.toCourseResponse(course);
            return ResponseEntity.ok(courseResponse);
        }
    }

    /**
     * Retrieves all courses.
     * @return a list of all courses.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<CourseResponse>> getAll() {
        List<Course> courseList = courseService.getAllCourses();
        List<CourseResponse> courseResponseList = courseList.stream()
                .map(CourseResponse::toCourseResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseResponseList);
    }

    /**
     * Updates an existing course by ID.
     * @param id the ID of the course to update.
     * @param editCourseRequest the updated course details.
     * @return the response containing updated course information or a not-found message.
     */
    @PutMapping("/update")
    public ResponseEntity<CourseResponse> update(@RequestParam int id, @RequestBody EditCourseRequest editCourseRequest) {
        Course course = courseService.editCourse(id, editCourseRequest);
        if (course == null) {
            CourseResponse courseResponse = new CourseResponse();
            courseResponse.setMessage("Course not found by id: " + id);
            return ResponseEntity.ofNullable(courseResponse);
        }
        CourseResponse courseResponse = CourseResponse.toCourseResponse(course);
        courseResponse.setMessage("Course with id: " + id + " updated successfully!");
        return ResponseEntity.ok(courseResponse);
    }

    /**
     * Deletes a course by ID.
     * @param id the ID of the course to delete.
     * @return the response containing a success or not-found message.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<CourseResponse> deleteById(@RequestParam int id) {
        Course course = courseService.deleteCourse(id);
        if (course == null) {
            CourseResponse courseResponse = new CourseResponse();
            courseResponse.setMessage("No course found with id: " + id);
            return ResponseEntity.ofNullable(courseResponse);
        }
        CourseResponse courseResponse = CourseResponse.toCourseResponse(course);
        courseResponse.setMessage("Course deleted successfully with id: " + id);
        return ResponseEntity.ok(courseResponse);
    }

    /**
     * Counts the total number of courses.
     * @return the total count of courses.
     */
    @GetMapping("/countAll")
    public long countCourses() {
        return courseRepository.count();
    }

    /**
     * Searches courses and instructors by a keyword.
     * @param key the search keyword.
     * @return a list of matching courses and instructors.
     */
    @GetMapping("/search")
    public List<CourseResponse> searchByCourseAndInstructor(@RequestParam String key) {
        return courseService.search(key);
    }
}

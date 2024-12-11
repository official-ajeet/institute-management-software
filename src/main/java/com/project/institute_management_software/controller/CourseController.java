package com.project.institute_management_software.controller;

import com.project.institute_management_software.dto.AddCourseRequest;
import com.project.institute_management_software.dto.CourseResponse;
import com.project.institute_management_software.dto.EditCourseRequest;
import com.project.institute_management_software.dto.StudentResponse;
import com.project.institute_management_software.entities.Course;
import com.project.institute_management_software.entities.Instructor;
import com.project.institute_management_software.entities.Student;
import com.project.institute_management_software.repository.CourseRepository;
import com.project.institute_management_software.repository.InstructorRepository;
import com.project.institute_management_software.repository.StudentRepository;
import com.project.institute_management_software.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/add")
    
    public ResponseEntity<CourseResponse>add(@RequestBody AddCourseRequest addCourseRequest){

        Course course = courseService.addCourse(addCourseRequest);
        CourseResponse courseResponse = CourseResponse.toCourseResponse(course);
        courseResponse.setMessage("Course Added Successfully!");
        return ResponseEntity.ok(courseResponse);
    }

    @GetMapping("/getById")
    public ResponseEntity<CourseResponse>getById(int id){
        Course course = courseService.getCourseById(id);
        CourseResponse courseResponse = new CourseResponse();
        if(course == null){
            courseResponse.setMessage("No course found with id: "+id);
            return ResponseEntity.ofNullable(courseResponse);
        }else{
            courseResponse = CourseResponse.toCourseResponse(course);
        }

        return ResponseEntity.ok(courseResponse);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CourseResponse>>getAll(){
        List<Course>courseList = courseService.getAllCourses();
        List<CourseResponse>courseResponseList = courseList.stream()
                .map(CourseResponse::toCourseResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseResponseList);
    }

    @PutMapping("/update")
    public ResponseEntity<CourseResponse>update(@RequestParam int id, @RequestBody EditCourseRequest editCourseRequest){
        Course course = courseService.editCourse(id, editCourseRequest);
        CourseResponse courseResponse = new CourseResponse();
        if(course == null){
            courseResponse.setMessage("Course not found by id: "+id);
            return ResponseEntity.ofNullable(courseResponse);
        }else{
            courseResponse = CourseResponse.toCourseResponse(course);
            courseResponse.setMessage("Course with id: "+id+"updated successfully!");
        }
        return  ResponseEntity.ok(courseResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CourseResponse>deleteById(@RequestParam int id){
        CourseResponse courseResponse = new CourseResponse();
        Course course = courseService.deleteCourse(id);
        if(course != null){
            courseResponse.setMessage("No course found with id: "+id);
            return  ResponseEntity.ofNullable(courseResponse)   ;
        }else{
            courseResponse = CourseResponse.toCourseResponse(course);
            courseResponse.setMessage("Course deleted successfully with id: "+id);
        }
        return ResponseEntity.ok(courseResponse);
    }

    @GetMapping("/countAll")
    public long countCourses(){
        return courseRepository.count();
    }

    @GetMapping("/search")
    public List<CourseResponse>searchByCourseAndInstructor(@RequestParam String key){
        return courseService.search(key);
    }

}

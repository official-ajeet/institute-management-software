package com.project.institute_management_software.services;

import com.project.institute_management_software.dto.AddStudentRequest;
import com.project.institute_management_software.dto.CourseResponse;
import com.project.institute_management_software.dto.EditStudentRequest;
import com.project.institute_management_software.dto.StudentResponse;
import com.project.institute_management_software.entities.Course;
import com.project.institute_management_software.entities.Instructor;
import com.project.institute_management_software.entities.Student;
import com.project.institute_management_software.repository.CourseRepository;
import com.project.institute_management_software.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;


    public Student addStudent(AddStudentRequest addStudentRequest){
        Student student = new Student();
        Course course = courseRepository.findById(addStudentRequest.getCourseId()).orElse(null);
        student.setName(addStudentRequest.getName());
        student.setEmail(addStudentRequest.getEmail());
        student.setMobile(addStudentRequest.getMobile());
        student.setEnrollmentDate(addStudentRequest.getEnrollmentDate());
        if(course != null){
            student.setCourse(course);
        }else{
            course = null;
        }
        return studentRepository.save(student);
    }

    public Student getStudentById(int studentId){
        return studentRepository.findById(studentId).orElse(null);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student editStudent(int id, EditStudentRequest editStudentRequest){
        Student existingStudent = studentRepository.findById(id).orElse(null);
        if(existingStudent != null){
            existingStudent.setName(editStudentRequest.getName());
            existingStudent.setEmail(editStudentRequest.getEmail());
            existingStudent.setEnrollmentDate(LocalDate.now());
            return studentRepository.save(existingStudent);
        }else return null;
    }

    public Student deleteStudentById(int id){
        Student existingStudent  = studentRepository.findById(id).orElse(null);
        if(existingStudent != null){
            studentRepository.deleteById(id);
        }
        return  existingStudent;
    }

    // Method to search courses by course name or instructor name
    public List<StudentResponse> search(String key) {
        // Get all courses and instructors
        List<Student> studentList = studentRepository.findAll();
        List<Course> courseList = courseRepository.findAll();

        // List to hold the search results
        List<Student> searchResult = new ArrayList<>();

        // Iterate over the courseList to find matches by course name or instructor name
        for (Student student : studentList) {
            // Check if the course name contains the key (case-insensitive)
            if (student.getName().toLowerCase().contains(key.toLowerCase())) {
                searchResult.add(student);
            } else {
                // If the instructor's name contains the key, add the course to the search result
                for (Course course : courseList) {
                    if (course.getInstructor().getName().toLowerCase().contains(key.toLowerCase())) {
                        searchResult.add(student);
                        break; // If we found a match, no need to continue checking instructors
                    }
                }
            }
        }

        // Convert the search result list to StudentResponse DTOs
        List<StudentResponse> studentResponses = searchResult.stream()
                .map(StudentResponse::toStudentResponse)
                .collect(Collectors.toList());

        // Return the search results as a list of Student objects
        return studentResponses;
    }
}

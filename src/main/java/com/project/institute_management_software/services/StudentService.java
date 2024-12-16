package com.project.institute_management_software.services;

import com.project.institute_management_software.dto.AddStudentRequest;
import com.project.institute_management_software.dto.CourseResponse;
import com.project.institute_management_software.dto.EditStudentRequest;
import com.project.institute_management_software.dto.StudentResponse;
import com.project.institute_management_software.entities.Course;
import com.project.institute_management_software.entities.Student;
import com.project.institute_management_software.repository.CourseRepository;
import com.project.institute_management_software.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Adds a new student and associates them with a course if the course ID is valid
    public Student addStudent(AddStudentRequest addStudentRequest) {
        Student student = new Student();

        // Set student details
        student.setName(addStudentRequest.getName());
        student.setEmail(addStudentRequest.getEmail());
        student.setMobile(addStudentRequest.getMobile());
        student.setEnrollmentDate(addStudentRequest.getEnrollmentDate());

        // Check for valid course and associate it
        courseRepository.findById(addStudentRequest.getCourseId()).ifPresent(student::setCourse);

        // Save only if a course is associated
        if (student.getCourse() != null) {
            return studentRepository.save(student);
        }

        return student; // Return unsaved student for validation in controller
    }

    // Retrieves a student by their ID
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    // Retrieves a list of all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Updates an existing student's details
    public Student editStudent(Long id, EditStudentRequest editStudentRequest) {
        Student existingStudent = studentRepository.findById(id).orElse(null);
        if (existingStudent != null) {
            existingStudent.setName(editStudentRequest.getName());
            existingStudent.setEmail(editStudentRequest.getEmail());
            existingStudent.setEnrollmentDate(LocalDate.now());
            return studentRepository.save(existingStudent);
        }
        return null;
    }

    // Deletes a student by their ID
    public Student deleteStudentById(Long id) {
        Student existingStudent = studentRepository.findById(id).orElse(null);
        if (existingStudent != null) {
            studentRepository.deleteById(id);
        }
        return existingStudent;
    }

    // Searches students by their name or the instructor's name
    public List<StudentResponse> search(String key) {
        List<Student> studentList = studentRepository.findAll();
        List<Course> courseList = courseRepository.findAll();
        List<Student> searchResult = new ArrayList<>();

        // Iterate through students and match with the search key
        for (Student student : studentList) {
            if (student.getName().toLowerCase().contains(key.toLowerCase())) {
                searchResult.add(student);
            } else {
                for (Course course : courseList) {
                    if (course.getInstructor().getName().toLowerCase().contains(key.toLowerCase())) {
                        searchResult.add(student);
                        break;
                    }
                }
            }
        }

        // Convert the search results into StudentResponse DTOs
        return searchResult.stream()
                .map(StudentResponse::toStudentResponse)
                .collect(Collectors.toList());
    }
}


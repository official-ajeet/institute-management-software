package com.project.institute_management_software.controller;

import com.project.institute_management_software.dto.AddStudentRequest;
import com.project.institute_management_software.dto.EditStudentRequest;
import com.project.institute_management_software.dto.StudentResponse;
import com.project.institute_management_software.entities.Student;
import com.project.institute_management_software.repository.StudentRepository;
import com.project.institute_management_software.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing student-related operations.
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Add a new student.
     *
     * @param addStudentRequest Request object containing student details.
     * @return Response containing the added student details or error message.
     */

    @PostMapping("/add")
    public ResponseEntity<StudentResponse> addStudent(@RequestBody AddStudentRequest addStudentRequest) {
        // Check if the student already exists
        Student existingStudent = studentRepository.findByEmail(addStudentRequest.getEmail());
        if (existingStudent != null) {
            return ResponseEntity.badRequest().body(
                    new StudentResponse("Student already exists with email: " + addStudentRequest.getEmail())
            );
        }
        if(addStudentRequest.getCourseId() == null){
            return ResponseEntity.badRequest().body(new StudentResponse("Please assign a valid course to the student."));
        }

        // Add student through the service layer
        Student student = studentService.addStudent(addStudentRequest);

        // Check if the student was associated with a course
        if (student.getCourse() == null) {
            return ResponseEntity.badRequest().body(
                    new StudentResponse("Please assign a valid course to the student.")
            );
        }

        // Build and return success response
        StudentResponse studentResponse = StudentResponse.toStudentResponse(student);
        studentResponse.setMessage("Student added successfully.");
        return ResponseEntity.ok(studentResponse);
    }


    /**
     * Retrieve a student by their ID.
     *
     * @param id The ID of the student.
     * @return Response containing student details or an error message.
     */
    @GetMapping("/getById")
    public ResponseEntity<StudentResponse> getStudentById(@RequestParam Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            StudentResponse response = new StudentResponse();
            response.setMessage("Student not found with ID: " + id);
            return ResponseEntity.ofNullable(response);
        }
        return ResponseEntity.ok(StudentResponse.toStudentResponse(student));
    }

    /**
     * Retrieve all students.
     *
     * @return List of all students.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        List<StudentResponse> responses = students.stream()
                .map(StudentResponse::toStudentResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Update an existing student's details.
     *
     * @param id                   The ID of the student to update.
     * @param editStudentRequest   Request object containing updated student details.
     * @return Response containing the updated student details or error message.
     */
    @PutMapping("/update")
    public ResponseEntity<StudentResponse> updateStudent(@RequestParam Long id, @RequestBody EditStudentRequest editStudentRequest) {
        Student student = studentService.editStudent(id, editStudentRequest);
        if (student == null) {
            StudentResponse response = new StudentResponse();
            response.setMessage("Student not found with ID: " + id + ". Update failed.");
            return ResponseEntity.ofNullable(response);
        }
        StudentResponse response = StudentResponse.toStudentResponse(student);
        response.setMessage("Student updated successfully with ID: " + id);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a student by their ID.
     *
     * @param id The ID of the student to delete.
     * @return Response containing success or error message.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<StudentResponse> deleteStudent(@RequestParam Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            StudentResponse response = new StudentResponse();
            response.setMessage("Student not found with ID: " + id);
            return ResponseEntity.ofNullable(response);
        }
        studentService.deleteStudentById(id);
        StudentResponse response = new StudentResponse();
        response.setMessage("Student deleted successfully with ID: " + id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get the total count of students.
     *
     * @return Total count of students.
     */
    @GetMapping("/countAll")
    public long countStudents() {
        return studentRepository.count();
    }

    /**
     * Search students by course or name.
     *
     * @param key Search keyword (e.g., course name or student name).
     * @return List of matching students.
     */
    @GetMapping("/search")
    public List<StudentResponse> searchStudents(@RequestParam String key) {
        return studentService.search(key);
    }
}

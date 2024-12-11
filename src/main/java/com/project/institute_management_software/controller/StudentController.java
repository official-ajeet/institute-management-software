package com.project.institute_management_software.controller;

import com.project.institute_management_software.dto.AddStudentRequest;
import com.project.institute_management_software.dto.CourseResponse;
import com.project.institute_management_software.dto.EditStudentRequest;
import com.project.institute_management_software.dto.StudentResponse;
import com.project.institute_management_software.entities.Course;
import com.project.institute_management_software.entities.Student;
import com.project.institute_management_software.repository.CourseRepository;
import com.project.institute_management_software.repository.StudentRepository;
import com.project.institute_management_software.services.CourseService;
import com.project.institute_management_software.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;


    @PostMapping("/add")
    public ResponseEntity<StudentResponse>addStudent(@RequestBody AddStudentRequest addStudentRequest){

        Student student = studentService.addStudent(addStudentRequest);
        StudentResponse studentResponse = StudentResponse.toStudentResponse(student);
        if(student.getCourse() == null){
            studentResponse.setMessage("Assign a valid course to student");
            return ResponseEntity.ofNullable(studentResponse);
        }else{
            studentResponse.setMessage("Student Added Successfully.");
            return ResponseEntity.ok(studentResponse);
        }

    }

    @GetMapping("/getById")
    public ResponseEntity<StudentResponse>getStudentById(@RequestParam int id){
        StudentResponse studentResponse = new StudentResponse();
        Student student = studentService.getStudentById(id);
        if(student!=null){
           studentResponse = StudentResponse.toStudentResponse(student);
        }else{
            studentResponse.setMessage("Student doesn't exist by id: "+id);
            return ResponseEntity.ofNullable(studentResponse);
        }

        return ResponseEntity.ok(studentResponse);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<StudentResponse>> getAll(){
        List<Student> studentList = studentService.getAllStudents();
        List<StudentResponse> studentResponse = studentList.stream()
                .map(StudentResponse::toStudentResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(studentResponse);

    }

    @PutMapping("/update")
    public ResponseEntity<StudentResponse>udpate(@RequestParam int id, @RequestBody EditStudentRequest editStudentRequest){
        Student student = studentService.editStudent(id, editStudentRequest );
        StudentResponse studentResponse = new StudentResponse();
        if(student != null){
            studentResponse = StudentResponse.toStudentResponse(student);

            studentResponse.setMessage("Student updated successfully with id: "+id);
            return ResponseEntity.ok(studentResponse);
        }else{
            studentResponse.setMessage("Student doesn't exists with id: "+id+"can not update");
            return ResponseEntity.ofNullable(studentResponse);
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<StudentResponse>delete(@RequestParam int id){
        Student existingStudent = studentRepository.findById(id).orElse(null);
        StudentResponse studentResponse = new StudentResponse();
        if(existingStudent == null){
            studentResponse.setMessage("Student doesn't exist by id: "+id);
            return  ResponseEntity.ofNullable(studentResponse);
        }else{
            existingStudent = studentService.deleteStudentById(id);
            studentResponse.setMessage("Student with id: "+id+"deleted successfully!");
        }
        return ResponseEntity.ok(studentResponse);
    }


    @GetMapping("/countAll")
    public long countAll(){
        return studentRepository.count();
    }

    @GetMapping("/search")
    public List<StudentResponse>searchByCourseAndStudent(@RequestParam String key){
        return studentService.search(key);
    }
}

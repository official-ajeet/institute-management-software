//package com.project.institute_management_software.services;
//
//import com.project.institute_management_software.dto.CourseResponse;
//import com.project.institute_management_software.dto.InstructorResponse;
//import com.project.institute_management_software.dto.StudentResponse;
//import com.project.institute_management_software.entities.Instructor;
//import com.project.institute_management_software.repository.InstructorRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDate;
//
//@Service
//public class StartupDataLoaderService {
//
//    @Autowired
//    private InstructorRepository instructorRepository;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public void loadInitialData() {
//        String courseApiUrl = "http://localhost:8080/api/course/add";
//        String studentApiUrl = "http://localhost:8080/api/student/add";
//
//        // Step 1: Create and Save Instructors
//        Instructor instructor1 = new Instructor();
//        instructor1.setName("John Doe");
//        instructor1.setEmail("john.doe@example.com");
//        instructor1.setSpecialization("Java & Spring Framework");
//        instructor1 = instructorRepository.save(instructor1);
//
//        Instructor instructor2 = new Instructor();
//        instructor2.setName("Jane Smith");
//        instructor2.setEmail("jane.smith@example.com");
//        instructor2.setSpecialization("Data Structures & Algorithms");
//        instructor2 = instructorRepository.save(instructor2);
//
//        // Step 2: Create Courses and Assign Instructors
//        CourseResponse course1 = new CourseResponse("Java Programming", "Learn the basics of Java programming.", "40 hours", instructor1.getId());
//        CourseResponse course2 = new CourseResponse("Spring Boot Mastery", "Comprehensive guide to Spring Boot.", "50 hours", instructor1.getId());
//        CourseResponse course3 = new CourseResponse("Data Structures and Algorithms", "Learn core DSA concepts with Java.", "45 hours", instructor2.getId());
//
//        // Post courses to the API
//        CourseResponse createdCourse1 = restTemplate.postForObject(courseApiUrl, course1, CourseResponse.class);
//        CourseResponse createdCourse2 = restTemplate.postForObject(courseApiUrl, course2, CourseResponse.class);
//        CourseResponse createdCourse3 = restTemplate.postForObject(courseApiUrl, course3, CourseResponse.class);
//
//        // Step 3: Create Students and Assign Courses
//        StudentResponse student1 = new StudentResponse("Alice", "alice@example.com", "1234567890", LocalDate.of(2024, 1, 1), createdCourse1.getCourseId());
//        StudentResponse student2 = new StudentResponse("Bob", "bob@example.com", "2345678901", LocalDate.of(2024, 3, 18), createdCourse2.getCourseId());
//        StudentResponse student3 = new StudentResponse("Charlie", "charlie@example.com", "3456789012", LocalDate.of(2024, 6, 25), createdCourse3.getCourseId());
//
//        // Post students to the API
//        restTemplate.postForEntity(studentApiUrl, student1, StudentResponse.class);
//        restTemplate.postForEntity(studentApiUrl, student2, StudentResponse.class);
//        restTemplate.postForEntity(studentApiUrl, student3, StudentResponse.class);
//    }
//}
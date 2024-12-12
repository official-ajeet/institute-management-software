package com.project.institute_management_software.controller;

import com.project.institute_management_software.dto.*;
import com.project.institute_management_software.entities.Instructor;
import com.project.institute_management_software.repository.InstructorRepository;
import com.project.institute_management_software.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing instructor-related operations.
 */
@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private InstructorRepository instructorRepository;

    /**
     * Add a new instructor.
     *
     * @param addInstructorRequest Request object containing instructor details.
     * @return Response containing the added instructor details.
     */
    @PostMapping("/add")
    public ResponseEntity<InstructorResponse> addInstructor(@RequestBody AddInstructorRequest addInstructorRequest) {
        Instructor instructor = instructorService.addInstructor(addInstructorRequest);
        InstructorResponse response = InstructorResponse.toInstructorResponse(instructor);
        response.setMessage("Instructor added successfully.");
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve an instructor by their ID.
     *
     * @param id The ID of the instructor.
     * @return Response containing the instructor details or an error message.
     */
    @GetMapping("/getById")
    public ResponseEntity<InstructorResponse> getInstructorById(@RequestParam int id) {
        Instructor instructor = instructorService.getInstructorById(id);
        if (instructor == null) {
            InstructorResponse response = new InstructorResponse();
            response.setMessage("Instructor not found with ID: " + id);
            return ResponseEntity.ofNullable(response);
        }
        return ResponseEntity.ok(InstructorResponse.toInstructorResponse(instructor));
    }

    /**
     * Retrieve all instructors.
     *
     * @return List of all instructors.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<InstructorResponse>> getAllInstructors() {
        List<Instructor> instructors = instructorService.getAllInstructors();
        List<InstructorResponse> responses = instructors.stream()
                .map(InstructorResponse::toInstructorResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Update an instructor's details.
     *
     * @param id                   The ID of the instructor to update.
     * @param editInstructorRequest Request object containing updated instructor details.
     * @return Response containing updated instructor details or an error message.
     */
    @PutMapping("/update")
    public ResponseEntity<InstructorResponse> updateInstructor(@RequestParam int id, @RequestBody EditInstructorRequest editInstructorRequest) {
        Instructor instructor = instructorService.editInstructor(id, editInstructorRequest);
        if (instructor == null) {
            InstructorResponse response = new InstructorResponse();
            response.setMessage("Instructor not found with ID: " + id + ". Update failed.");
            return ResponseEntity.ofNullable(response);
        }
        InstructorResponse response = InstructorResponse.toInstructorResponse(instructor);
        response.setMessage("Instructor updated successfully with ID: " + id);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete an instructor by their ID.
     *
     * @param id The ID of the instructor to delete.
     * @return Response containing success or error message.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<InstructorResponse> deleteInstructor(@RequestParam int id) {
        Instructor instructor = instructorService.deleteInstructor(id);
        InstructorResponse response = new InstructorResponse();
        if (instructor == null) {
            response.setMessage("Instructor not found with ID: " + id);
            return ResponseEntity.ofNullable(response);
        }
        response.setMessage("Instructor deleted successfully with ID: " + id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get the total count of instructors.
     *
     * @return Total count of instructors.
     */
    @GetMapping("/countAll")
    public long countInstructors() {
        return instructorRepository.count();
    }
}


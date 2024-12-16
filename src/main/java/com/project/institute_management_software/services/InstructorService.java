package com.project.institute_management_software.services;

import com.project.institute_management_software.dto.AddInstructorRequest;
import com.project.institute_management_software.dto.EditInstructorRequest;
import com.project.institute_management_software.entities.Instructor;
import com.project.institute_management_software.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    // Adds a new instructor to the system
    public Instructor addInstructor(AddInstructorRequest addInstructorRequest) {
        Instructor instructor = new Instructor();
        instructor.setName(addInstructorRequest.getName());
        instructor.setEmail(addInstructorRequest.getEmail());
        instructor.setSpecialization(addInstructorRequest.getSpecialization());
        return instructorRepository.save(instructor);
    }

    // Retrieves an instructor by their ID
    public Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id).orElse(null);
    }

    // Retrieves a list of all instructors
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    // Updates the details of an existing instructor
    public Instructor editInstructor(Long id, EditInstructorRequest editInstructor) {
        Instructor instructor = instructorRepository.findById(id).orElse(null);
        if (instructor != null) {
            instructor.setName(editInstructor.getName());
            instructor.setEmail(editInstructor.getEmail());
            instructor.setSpecialization(editInstructor.getSpecialization());
        }
        return instructorRepository.save(instructor);
    }

    // Deletes an instructor by their ID
    public Instructor deleteInstructor(Long id) {
        Instructor existingInstructor = instructorRepository.findById(id).orElse(null);
        if (existingInstructor != null) {
            instructorRepository.deleteById(id);
        }
        return existingInstructor;
    }
}


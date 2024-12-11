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

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private InstructorRepository instructorRepository;
    
    @PostMapping("/add")
    public ResponseEntity<InstructorResponse> addInstructor(@RequestBody AddInstructorRequest addInstructorRequest){

        Instructor instructor = instructorService.addInstructor(addInstructorRequest);

        InstructorResponse instructorResponse = InstructorResponse.toInstructorResponse(instructor);

        instructorResponse.setMessage("Instructor Added Successfully.");
        return ResponseEntity.ok(instructorResponse);
    }

    @GetMapping("/getById")
    public ResponseEntity<InstructorResponse>getInstructorById(@RequestParam int id){
        InstructorResponse instructorResponse = new InstructorResponse();
         Instructor instructor = instructorService.getInstructorById(id);
        if(instructor!=null){
            instructorResponse = InstructorResponse.toInstructorResponse(instructor);
        }else{
            instructorResponse.setMessage("instructor doesn't exist by id: "+id);
            return ResponseEntity.ofNullable(instructorResponse);
        }

        return ResponseEntity.ok(instructorResponse);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<InstructorResponse>> getAll(){
        List<Instructor> instructorList = instructorService.getAllInstructors();
        List<InstructorResponse> instructorResponse = instructorList.stream()
                .map(InstructorResponse::toInstructorResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(instructorResponse);

    }

    @PutMapping("/update")
    public ResponseEntity<InstructorResponse>udpate(@RequestParam int id, @RequestBody EditInstructorRequest editinstructorRequest){
        Instructor instructor = instructorService.editInstructor(id, editinstructorRequest );
        InstructorResponse instructorResponse = new InstructorResponse();
        if(instructor != null){

            instructorResponse = InstructorResponse.toInstructorResponse(instructor);
            instructorResponse.setMessage("instructor updated successfully with id: "+id);
            return ResponseEntity.ok(instructorResponse);
        }else{
            instructorResponse.setMessage("instructor doesn't exists with id: "+id+"can not update");
            return ResponseEntity.ofNullable(instructorResponse);
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<InstructorResponse>delete(@RequestParam int id){
        Instructor instructor = instructorService.deleteInstructor(id);
        InstructorResponse instructorResponse = new InstructorResponse();
        if(instructor == null){
            instructorResponse.setMessage("instructor doesn't exist by id: "+id);
            return  ResponseEntity.ofNullable(instructorResponse);
        }else{
            instructorResponse.setMessage("instructor with id: "+id+"deleted successfully!");
        }
        return ResponseEntity.ok(instructorResponse);

    }

    @GetMapping("/countAll")
    public long countAll(){
        return instructorRepository.count();
    }

}

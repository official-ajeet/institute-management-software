package com.project.institute_management_software.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddCourseRequest {
    private String name;
    private String description;
    private String duration;
    private int instructorId;

}

package com.example.HospitaManagmentSystemDemo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecializationCreateRequest {
    @NotBlank @Size(max = 50) private String code;
    @NotBlank @Size(max = 200) private String name;
    @Size(max = 500) private String description;
    @NotNull(message = "Department is required") private Long departmentId;
}

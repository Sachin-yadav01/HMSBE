package com.example.HospitaManagmentSystemDemo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffActivationRequest {

    @NotNull(message = "Active status is required")
    private Boolean active;
    
    private String reason;
}

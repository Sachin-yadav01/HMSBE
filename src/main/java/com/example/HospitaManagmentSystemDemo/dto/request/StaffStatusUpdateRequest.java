package com.example.HospitaManagmentSystemDemo.dto.request;

import com.example.HospitaManagmentSystemDemo.constant.StaffStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StaffStatusUpdateRequest {
    
    @NotNull(message = "Status is required")
    private StaffStatus status;
    
    private String reason;
    
    private LocalDate effectiveDate;
}

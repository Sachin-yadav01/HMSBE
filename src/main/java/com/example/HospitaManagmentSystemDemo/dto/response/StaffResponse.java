package com.example.HospitaManagmentSystemDemo.dto.response;

import com.example.HospitaManagmentSystemDemo.constant.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class StaffResponse {
    private Long id;
    private UUID staffUuid;
    private String employeeCode;
    private String fullName;
    private Title title;
    private String firstName;
    private String middleName;
    private String lastName;
    private StaffType staffType;
    private Gender gender;
    private LocalDate dateOfBirth;
    
    private String phonePrimary;
    private String phoneSecondary;
    private String email;
    
    private Long departmentId;
    private String departmentName; // To be populated if another service/entity provides it, else keep it as ID or null for now.
    
    private Long designationId;
    private String designationName;
    
    private EmploymentType employmentType;
    private LocalDate joiningDate;
    
    private Long reportingManagerId;
    private String reportingManagerName;
    
    private Long shiftId;
    private DayOfWeek weeklyOffDay;
    
    private StaffStatus status;
    private Boolean isActive;
    
    private String qualification;
    private String specialization;
    private Integer experienceYears;
    private String registrationNumber;
    private String registrationCouncil;
    private LocalDate registrationExpiryDate;
    
    private String profileImageUrl;
    
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}

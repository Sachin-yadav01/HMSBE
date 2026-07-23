package com.example.HospitaManagmentSystemDemo.dto.request;

import com.example.HospitaManagmentSystemDemo.constant.EmploymentType;
import com.example.HospitaManagmentSystemDemo.constant.Gender;
import com.example.HospitaManagmentSystemDemo.constant.StaffType;
import com.example.HospitaManagmentSystemDemo.constant.Title;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Setter
public class StaffPatchRequest {
    
    private StaffType staffType;
    private Title title;

    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    private String firstName;

    @Size(max = 100, message = "Middle name must not exceed 100 characters")
    private String middleName;

    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    private Gender gender;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    private String profileImageUrl;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Primary phone number is invalid")
    private String phonePrimary;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Secondary phone number is invalid")
    private String phoneSecondary;

    @Email(message = "Email must be valid")
    private String email;

    @Size(max = 100, message = "Emergency contact name must not exceed 100 characters")
    private String emergencyContactName;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Emergency contact phone is invalid")
    private String emergencyContactPhone;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String district;
    private String state;
    private String postalCode;
    private String country;
    
    private Long departmentId;
    private Long designationId;
    private EmploymentType employmentType;
    private LocalDate joiningDate;
    private LocalDate probationEndDate;
    private Long reportingManagerId;
    private Long shiftId;
    private DayOfWeek weeklyOffDay;
    private String qualification;
    private String specialization;

    @Min(value = 0, message = "Experience years cannot be negative")
    private Integer experienceYears;

    private String registrationNumber;
    private String registrationCouncil;
    private LocalDate registrationExpiryDate;
}

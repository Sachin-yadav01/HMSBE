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
public class StaffUpdateRequest {
    
    @NotNull(message = "Staff type is required")
    private StaffType staffType;

    @NotNull(message = "Title is required")
    private Title title;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    private String firstName;

    @Size(max = 100, message = "Middle name must not exceed 100 characters")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    private String profileImageUrl;

    @NotBlank(message = "Primary phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Primary phone number is invalid")
    private String phonePrimary;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Secondary phone number is invalid")
    private String phoneSecondary;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Size(max = 100, message = "Emergency contact name must not exceed 100 characters")
    private String emergencyContactName;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Emergency contact phone is invalid")
    private String emergencyContactPhone;

    @NotBlank(message = "Address line 1 is required")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "City is required")
    private String city;

    private String district;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Postal code is required")
    private String postalCode;

    @NotBlank(message = "Country is required")
    private String country;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Designation ID is required")
    private Long designationId;

    @NotNull(message = "Employment type is required")
    private EmploymentType employmentType;

    @NotNull(message = "Joining date is required")
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

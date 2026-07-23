package com.example.HospitaManagmentSystemDemo.entity;

import com.example.HospitaManagmentSystemDemo.constant.EmploymentType;
import com.example.HospitaManagmentSystemDemo.constant.Gender;
import com.example.HospitaManagmentSystemDemo.constant.StaffStatus;
import com.example.HospitaManagmentSystemDemo.constant.StaffType;
import com.example.HospitaManagmentSystemDemo.constant.Title;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    @Builder.Default
    private UUID staffUuid = UUID.randomUUID();

    @Column(nullable = false, unique = true, updatable = false, length = 50)
    private String employeeCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StaffType staffType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Title title;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(length = 100)
    private String middleName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    private String profileImageUrl;

    @Column(nullable = false, unique = true, length = 20)
    private String phonePrimary;

    @Column(length = 20)
    private String phoneSecondary;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 100)
    private String emergencyContactName;

    @Column(length = 20)
    private String emergencyContactPhone;

    @Column(nullable = false)
    private String addressLine1;

    private String addressLine2;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(length = 100)
    private String district;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(nullable = false, length = 20)
    private String postalCode;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false)
    private Long departmentId;

    @Column(nullable = false)
    private Long designationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EmploymentType employmentType;

    @Column(nullable = false)
    private LocalDate joiningDate;

    private LocalDate probationEndDate;

    private Long reportingManagerId;

    private Long shiftId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private DayOfWeek weeklyOffDay;

    private String qualification;

    private String specialization;

    private Integer experienceYears;

    @Column(unique = true, length = 100)
    private String registrationNumber;

    @Column(length = 100)
    private String registrationCouncil;

    private LocalDate registrationExpiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StaffStatus status;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    private LocalDateTime deletedAt;

    private Long deletedBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Long createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedOn;

    @LastModifiedBy
    @Column(nullable = false)
    private Long updatedBy;

    @Version
    @Column(nullable = false)
    private Long version;
}

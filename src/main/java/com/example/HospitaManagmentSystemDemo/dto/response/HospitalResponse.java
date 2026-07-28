package com.example.HospitaManagmentSystemDemo.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalResponse {
    private Long id;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phonePrimary;
    private String phoneSecondary;
    private String email;
    private String registrationNo;
    private String gstin;
    private String logoUrl;
    private Boolean active;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}

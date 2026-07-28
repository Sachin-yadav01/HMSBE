package com.example.HospitaManagmentSystemDemo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalUpdateRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 200)
    private String name;

    @Size(max = 255)
    private String addressLine1;

    @Size(max = 255)
    private String addressLine2;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String state;

    @Size(max = 20)
    private String postalCode;

    @Size(max = 100)
    private String country;

    @Size(max = 20)
    private String phonePrimary;

    @Size(max = 20)
    private String phoneSecondary;

    @Size(max = 100)
    private String email;

    @Size(max = 100)
    private String registrationNo;

    @Size(max = 50)
    private String gstin;

    @Size(max = 500)
    private String logoUrl;
}

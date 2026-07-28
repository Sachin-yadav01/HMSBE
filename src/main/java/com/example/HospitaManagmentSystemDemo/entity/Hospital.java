package com.example.HospitaManagmentSystemDemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "hospitals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital extends BaseMasterEntity {

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 255)
    private String addressLine1;

    @Column(length = 255)
    private String addressLine2;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 20)
    private String postalCode;

    @Column(length = 100)
    private String country;

    @Column(length = 20)
    private String phonePrimary;

    @Column(length = 20)
    private String phoneSecondary;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String registrationNo;

    @Column(length = 50)
    private String gstin;

    @Column(length = 500)
    private String logoUrl;
}

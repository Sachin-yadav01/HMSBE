package com.example.HospitaManagmentSystemDemo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicine_frequencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineFrequency extends BaseMasterEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 500)
    private String description;

}

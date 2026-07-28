package com.example.HospitaManagmentSystemDemo.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceCategoryResponse {
    private Long id;
    private String code;
    private String name;
    private String description;

    private Boolean active;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}

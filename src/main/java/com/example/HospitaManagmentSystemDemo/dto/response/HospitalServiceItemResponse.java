package com.example.HospitaManagmentSystemDemo.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HospitalServiceItemResponse {
    private Long id;
    private String serviceCode;
    private String name;
    private String description;
    private Long categoryId;
    private String categoryName;
    private BigDecimal rate;
    private BigDecimal taxPct;
    private Boolean active;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}

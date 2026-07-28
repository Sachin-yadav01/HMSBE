package com.example.HospitaManagmentSystemDemo.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConsultationTypeResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Integer defaultDurationMin;
    private BigDecimal defaultFee;
    private Boolean active;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}

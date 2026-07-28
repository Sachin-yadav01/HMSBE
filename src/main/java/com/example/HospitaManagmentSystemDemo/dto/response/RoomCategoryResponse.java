package com.example.HospitaManagmentSystemDemo.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RoomCategoryResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal defaultRate;
    private Boolean active;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}

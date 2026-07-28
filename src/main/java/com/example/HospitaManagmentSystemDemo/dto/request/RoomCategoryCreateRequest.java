package com.example.HospitaManagmentSystemDemo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class RoomCategoryCreateRequest {
    @NotBlank @Size(max = 50) private String code;
    @NotBlank @Size(max = 200) private String name;
    @Size(max = 500) private String description;
    private BigDecimal defaultRate;
}

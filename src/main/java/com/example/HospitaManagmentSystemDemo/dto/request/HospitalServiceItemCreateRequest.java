package com.example.HospitaManagmentSystemDemo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class HospitalServiceItemCreateRequest {
    @NotBlank @Size(max = 50) private String serviceCode;
    @NotBlank @Size(max = 200) private String name;
    @Size(max = 500) private String description;
    @NotNull(message = "Category is required") private Long categoryId;
    @NotNull(message = "Rate is required") private BigDecimal rate;
    private BigDecimal taxPct;
}

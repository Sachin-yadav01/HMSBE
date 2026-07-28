package com.example.HospitaManagmentSystemDemo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecializationSearchCriteria {
    private String searchText;
    private Boolean active;
    private Long departmentId;
}

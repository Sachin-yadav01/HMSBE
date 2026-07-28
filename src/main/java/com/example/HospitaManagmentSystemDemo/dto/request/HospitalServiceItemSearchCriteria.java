package com.example.HospitaManagmentSystemDemo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HospitalServiceItemSearchCriteria {
    private String searchText;
    private Boolean active;
    private Long categoryId;
}

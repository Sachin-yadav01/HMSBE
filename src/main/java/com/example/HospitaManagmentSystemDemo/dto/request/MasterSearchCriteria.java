package com.example.HospitaManagmentSystemDemo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterSearchCriteria {
    private String searchText;
    private Boolean active;
}

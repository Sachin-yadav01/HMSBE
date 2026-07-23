package com.example.HospitaManagmentSystemDemo.dto.request;

import com.example.HospitaManagmentSystemDemo.constant.EmploymentType;
import com.example.HospitaManagmentSystemDemo.constant.StaffStatus;
import com.example.HospitaManagmentSystemDemo.constant.StaffType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StaffSearchCriteria {
    private String searchText;
    private StaffType staffType;
    private Long departmentId;
    private Long designationId;
    private EmploymentType employmentType;
    private StaffStatus status;
    private Boolean active;
    private LocalDate joiningDateFrom;
    private LocalDate joiningDateTo;
}

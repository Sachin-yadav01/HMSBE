package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.DepartmentCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.DepartmentUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.DepartmentResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse create(DepartmentCreateRequest request);
    DepartmentResponse getById(Long id);
    DepartmentResponse update(Long id, DepartmentUpdateRequest request);
    DepartmentResponse updateActivation(Long id, MasterActivationRequest request);
    void delete(Long id);
    PageResponse<DepartmentResponse> search(MasterSearchCriteria criteria, Pageable pageable);
    List<DepartmentResponse> listActive();
}

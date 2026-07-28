package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.LabTestCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.LabTestCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.LabTestCategoryResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LabTestCategoryService {
    LabTestCategoryResponse create(LabTestCategoryCreateRequest request);
    LabTestCategoryResponse getById(Long id);
    LabTestCategoryResponse update(Long id, LabTestCategoryUpdateRequest request);
    LabTestCategoryResponse updateActivation(Long id, MasterActivationRequest request);
    void delete(Long id);
    PageResponse<LabTestCategoryResponse> search(MasterSearchCriteria criteria, Pageable pageable);
    List<LabTestCategoryResponse> listActive();
}

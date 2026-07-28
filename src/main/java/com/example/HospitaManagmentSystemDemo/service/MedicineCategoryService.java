package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.MedicineCategoryResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicineCategoryService {
    MedicineCategoryResponse create(MedicineCategoryCreateRequest request);
    MedicineCategoryResponse getById(Long id);
    MedicineCategoryResponse update(Long id, MedicineCategoryUpdateRequest request);
    MedicineCategoryResponse updateActivation(Long id, MasterActivationRequest request);
    void delete(Long id);
    PageResponse<MedicineCategoryResponse> search(MasterSearchCriteria criteria, Pageable pageable);
    List<MedicineCategoryResponse> listActive();
}

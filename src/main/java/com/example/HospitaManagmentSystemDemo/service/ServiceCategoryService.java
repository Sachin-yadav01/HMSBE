package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.ServiceCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.ServiceCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.ServiceCategoryResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceCategoryService {
    ServiceCategoryResponse create(ServiceCategoryCreateRequest request);
    ServiceCategoryResponse getById(Long id);
    ServiceCategoryResponse update(Long id, ServiceCategoryUpdateRequest request);
    ServiceCategoryResponse updateActivation(Long id, MasterActivationRequest request);
    void delete(Long id);
    PageResponse<ServiceCategoryResponse> search(MasterSearchCriteria criteria, Pageable pageable);
    List<ServiceCategoryResponse> listActive();
}

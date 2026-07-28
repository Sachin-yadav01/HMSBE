package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.SpecializationCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.SpecializationSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.SpecializationUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.SpecializationResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SpecializationService {
    SpecializationResponse create(SpecializationCreateRequest request);
    SpecializationResponse getById(Long id);
    SpecializationResponse update(Long id, SpecializationUpdateRequest request);
    SpecializationResponse updateActivation(Long id, MasterActivationRequest request);
    void delete(Long id);
    PageResponse<SpecializationResponse> search(SpecializationSearchCriteria criteria, Pageable pageable);
    List<SpecializationResponse> listActive();
}

package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.ConsultationTypeCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.ConsultationTypeUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.response.ConsultationTypeResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ConsultationTypeService {
    ConsultationTypeResponse create(ConsultationTypeCreateRequest request);
    ConsultationTypeResponse getById(Long id);
    ConsultationTypeResponse update(Long id, ConsultationTypeUpdateRequest request);
    ConsultationTypeResponse updateActivation(Long id, MasterActivationRequest request);
    void delete(Long id);
    PageResponse<ConsultationTypeResponse> search(MasterSearchCriteria criteria, Pageable pageable);
    List<ConsultationTypeResponse> listActive();
}

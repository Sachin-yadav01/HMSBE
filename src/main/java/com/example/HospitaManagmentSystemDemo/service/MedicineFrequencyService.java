package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineFrequencyCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineFrequencyUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.MedicineFrequencyResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicineFrequencyService {
    MedicineFrequencyResponse create(MedicineFrequencyCreateRequest request);
    MedicineFrequencyResponse getById(Long id);
    MedicineFrequencyResponse update(Long id, MedicineFrequencyUpdateRequest request);
    MedicineFrequencyResponse updateActivation(Long id, MasterActivationRequest request);
    void delete(Long id);
    PageResponse<MedicineFrequencyResponse> search(MasterSearchCriteria criteria, Pageable pageable);
    List<MedicineFrequencyResponse> listActive();
}

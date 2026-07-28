package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineRouteCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineRouteUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.MedicineRouteResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicineRouteService {
    MedicineRouteResponse create(MedicineRouteCreateRequest request);
    MedicineRouteResponse getById(Long id);
    MedicineRouteResponse update(Long id, MedicineRouteUpdateRequest request);
    MedicineRouteResponse updateActivation(Long id, MasterActivationRequest request);
    void delete(Long id);
    PageResponse<MedicineRouteResponse> search(MasterSearchCriteria criteria, Pageable pageable);
    List<MedicineRouteResponse> listActive();
}

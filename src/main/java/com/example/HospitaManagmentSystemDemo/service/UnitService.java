package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.UnitCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.UnitUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.UnitResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UnitService {
    UnitResponse create(UnitCreateRequest request);
    UnitResponse getById(Long id);
    UnitResponse update(Long id, UnitUpdateRequest request);
    UnitResponse updateActivation(Long id, MasterActivationRequest request);
    void delete(Long id);
    PageResponse<UnitResponse> search(MasterSearchCriteria criteria, Pageable pageable);
    List<UnitResponse> listActive();
}

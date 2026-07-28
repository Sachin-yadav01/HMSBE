package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.HospitalServiceItemCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.HospitalServiceItemSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.HospitalServiceItemUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.HospitalServiceItemResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ServiceCatalogService {
    HospitalServiceItemResponse create(HospitalServiceItemCreateRequest request);
    HospitalServiceItemResponse getById(Long id);
    HospitalServiceItemResponse update(Long id, HospitalServiceItemUpdateRequest request);
    HospitalServiceItemResponse updateActivation(Long id, MasterActivationRequest request);
    void delete(Long id);
    PageResponse<HospitalServiceItemResponse> search(HospitalServiceItemSearchCriteria criteria, Pageable pageable);
    List<HospitalServiceItemResponse> listActive();
}

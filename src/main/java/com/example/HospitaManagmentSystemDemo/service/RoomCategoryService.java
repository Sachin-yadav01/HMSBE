package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.RoomCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.RoomCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.RoomCategoryResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RoomCategoryService {
    RoomCategoryResponse create(RoomCategoryCreateRequest request);
    RoomCategoryResponse getById(Long id);
    RoomCategoryResponse update(Long id, RoomCategoryUpdateRequest request);
    RoomCategoryResponse updateActivation(Long id, MasterActivationRequest request);
    void delete(Long id);
    PageResponse<RoomCategoryResponse> search(MasterSearchCriteria criteria, Pageable pageable);
    List<RoomCategoryResponse> listActive();
}

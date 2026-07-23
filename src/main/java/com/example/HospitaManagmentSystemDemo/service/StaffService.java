package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.StaffActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffPatchRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffStatusUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.StaffResponse;
import org.springframework.data.domain.Pageable;

public interface StaffService {

    StaffResponse createStaff(StaffCreateRequest request);

    StaffResponse getStaffById(Long staffId);

    StaffResponse getStaffByEmployeeCode(String employeeCode);

    StaffResponse updateStaff(Long staffId, StaffUpdateRequest request);

    StaffResponse patchStaff(Long staffId, StaffPatchRequest request);

    StaffResponse updateStaffStatus(Long staffId, StaffStatusUpdateRequest request);

    StaffResponse updateStaffActivation(Long staffId, StaffActivationRequest request);

    PageResponse<StaffResponse> searchStaff(StaffSearchCriteria criteria, Pageable pageable);

    void deleteStaff(Long staffId);
}

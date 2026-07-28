package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.HospitalUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.HospitalResponse;

public interface HospitalService {
    HospitalResponse getProfile();
    HospitalResponse updateProfile(HospitalUpdateRequest request);
}

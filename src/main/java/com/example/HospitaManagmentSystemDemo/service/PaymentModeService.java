package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.PaymentModeCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.PaymentModeUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PaymentModeResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentModeService {
    PaymentModeResponse create(PaymentModeCreateRequest request);
    PaymentModeResponse getById(Long id);
    PaymentModeResponse update(Long id, PaymentModeUpdateRequest request);
    PaymentModeResponse updateActivation(Long id, MasterActivationRequest request);
    void delete(Long id);
    PageResponse<PaymentModeResponse> search(MasterSearchCriteria criteria, Pageable pageable);
    List<PaymentModeResponse> listActive();
}

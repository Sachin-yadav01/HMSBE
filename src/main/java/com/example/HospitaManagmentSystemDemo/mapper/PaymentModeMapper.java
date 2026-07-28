package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.PaymentModeCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.PaymentModeUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PaymentModeResponse;
import com.example.HospitaManagmentSystemDemo.entity.PaymentMode;
import org.springframework.stereotype.Component;

@Component
public class PaymentModeMapper {

    public PaymentMode toEntity(PaymentModeCreateRequest request) {
        return PaymentMode.builder()
                .code(request.getCode().trim())
                .name(request.getName().trim())
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .requiresRefNo(request.getRequiresRefNo() != null ? request.getRequiresRefNo() : false)
                .build();
    }

    public void updateEntity(PaymentMode entity, PaymentModeUpdateRequest request) {
        entity.setCode(request.getCode().trim());
        entity.setName(request.getName().trim());
        entity.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
        entity.setRequiresRefNo(request.getRequiresRefNo());
    }

    public PaymentModeResponse toResponse(PaymentMode entity) {
        return PaymentModeResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .requiresRefNo(entity.getRequiresRefNo())
                .active(entity.getActive())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }
}

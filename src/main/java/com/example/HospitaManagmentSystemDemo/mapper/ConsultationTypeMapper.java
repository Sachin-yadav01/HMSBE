package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.ConsultationTypeCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.ConsultationTypeUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.ConsultationTypeResponse;
import com.example.HospitaManagmentSystemDemo.entity.ConsultationType;
import org.springframework.stereotype.Component;

@Component
public class ConsultationTypeMapper {
    public ConsultationType toEntity(ConsultationTypeCreateRequest request) {
        return ConsultationType.builder()
                .code(request.getCode().trim())
                .name(request.getName().trim())
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .defaultDurationMin(request.getDefaultDurationMin())
                .defaultFee(request.getDefaultFee())
                .build();
    }

    public void updateEntity(ConsultationType entity, ConsultationTypeUpdateRequest request) {
        entity.setCode(request.getCode().trim());
        entity.setName(request.getName().trim());
        entity.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
        entity.setDefaultDurationMin(request.getDefaultDurationMin());
        entity.setDefaultFee(request.getDefaultFee());
    }

    public ConsultationTypeResponse toResponse(ConsultationType entity) {
        return ConsultationTypeResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .defaultDurationMin(entity.getDefaultDurationMin())
                .defaultFee(entity.getDefaultFee())
                .active(entity.getActive())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }
}

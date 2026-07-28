package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.MedicineFrequencyCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineFrequencyUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.MedicineFrequencyResponse;
import com.example.HospitaManagmentSystemDemo.entity.MedicineFrequency;
import org.springframework.stereotype.Component;

@Component
public class MedicineFrequencyMapper {

    public MedicineFrequency toEntity(MedicineFrequencyCreateRequest request) {
        return MedicineFrequency.builder()
                .code(request.getCode().trim())
                .name(request.getName().trim())
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .build();
    }

    public void updateEntity(MedicineFrequency entity, MedicineFrequencyUpdateRequest request) {
        entity.setCode(request.getCode().trim());
        entity.setName(request.getName().trim());
        entity.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
    }

    public MedicineFrequencyResponse toResponse(MedicineFrequency entity) {
        return MedicineFrequencyResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .active(entity.getActive())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }
}

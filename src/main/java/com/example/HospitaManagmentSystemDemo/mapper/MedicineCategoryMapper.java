package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.MedicineCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.MedicineCategoryResponse;
import com.example.HospitaManagmentSystemDemo.entity.MedicineCategory;
import org.springframework.stereotype.Component;

@Component
public class MedicineCategoryMapper {

    public MedicineCategory toEntity(MedicineCategoryCreateRequest request) {
        return MedicineCategory.builder()
                .code(request.getCode().trim())
                .name(request.getName().trim())
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .build();
    }

    public void updateEntity(MedicineCategory entity, MedicineCategoryUpdateRequest request) {
        entity.setCode(request.getCode().trim());
        entity.setName(request.getName().trim());
        entity.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
    }

    public MedicineCategoryResponse toResponse(MedicineCategory entity) {
        return MedicineCategoryResponse.builder()
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

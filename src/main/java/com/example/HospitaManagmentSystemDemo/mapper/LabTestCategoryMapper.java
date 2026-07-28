package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.LabTestCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.LabTestCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.LabTestCategoryResponse;
import com.example.HospitaManagmentSystemDemo.entity.LabTestCategory;
import org.springframework.stereotype.Component;

@Component
public class LabTestCategoryMapper {

    public LabTestCategory toEntity(LabTestCategoryCreateRequest request) {
        return LabTestCategory.builder()
                .code(request.getCode().trim())
                .name(request.getName().trim())
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .build();
    }

    public void updateEntity(LabTestCategory entity, LabTestCategoryUpdateRequest request) {
        entity.setCode(request.getCode().trim());
        entity.setName(request.getName().trim());
        entity.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
    }

    public LabTestCategoryResponse toResponse(LabTestCategory entity) {
        return LabTestCategoryResponse.builder()
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

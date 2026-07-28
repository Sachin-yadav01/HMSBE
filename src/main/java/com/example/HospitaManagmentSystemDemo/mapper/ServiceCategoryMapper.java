package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.ServiceCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.ServiceCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.ServiceCategoryResponse;
import com.example.HospitaManagmentSystemDemo.entity.ServiceCategory;
import org.springframework.stereotype.Component;

@Component
public class ServiceCategoryMapper {

    public ServiceCategory toEntity(ServiceCategoryCreateRequest request) {
        return ServiceCategory.builder()
                .code(request.getCode().trim())
                .name(request.getName().trim())
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .build();
    }

    public void updateEntity(ServiceCategory entity, ServiceCategoryUpdateRequest request) {
        entity.setCode(request.getCode().trim());
        entity.setName(request.getName().trim());
        entity.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
    }

    public ServiceCategoryResponse toResponse(ServiceCategory entity) {
        return ServiceCategoryResponse.builder()
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

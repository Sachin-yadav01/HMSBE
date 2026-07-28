package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.SpecializationCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.SpecializationUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.SpecializationResponse;
import com.example.HospitaManagmentSystemDemo.entity.Specialization;
import org.springframework.stereotype.Component;

@Component
public class SpecializationMapper {
    public Specialization toEntity(SpecializationCreateRequest request) {
        return Specialization.builder()
                .code(request.getCode().trim())
                .name(request.getName().trim())
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .departmentId(request.getDepartmentId())
                .build();
    }

    public void updateEntity(Specialization entity, SpecializationUpdateRequest request) {
        entity.setCode(request.getCode().trim());
        entity.setName(request.getName().trim());
        entity.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
        entity.setDepartmentId(request.getDepartmentId());
    }

    public SpecializationResponse toResponse(Specialization entity, String departmentName) {
        return SpecializationResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .departmentId(entity.getDepartmentId())
                .departmentName(departmentName)
                .active(entity.getActive())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }
}

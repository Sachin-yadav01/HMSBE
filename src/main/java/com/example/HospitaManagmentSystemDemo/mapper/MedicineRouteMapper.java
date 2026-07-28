package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.MedicineRouteCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineRouteUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.MedicineRouteResponse;
import com.example.HospitaManagmentSystemDemo.entity.MedicineRoute;
import org.springframework.stereotype.Component;

@Component
public class MedicineRouteMapper {

    public MedicineRoute toEntity(MedicineRouteCreateRequest request) {
        return MedicineRoute.builder()
                .code(request.getCode().trim())
                .name(request.getName().trim())
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .build();
    }

    public void updateEntity(MedicineRoute entity, MedicineRouteUpdateRequest request) {
        entity.setCode(request.getCode().trim());
        entity.setName(request.getName().trim());
        entity.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
    }

    public MedicineRouteResponse toResponse(MedicineRoute entity) {
        return MedicineRouteResponse.builder()
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

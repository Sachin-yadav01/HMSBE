package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.UnitCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.UnitUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.UnitResponse;
import com.example.HospitaManagmentSystemDemo.entity.Unit;
import org.springframework.stereotype.Component;

@Component
public class UnitMapper {

    public Unit toEntity(UnitCreateRequest request) {
        return Unit.builder()
                .code(request.getCode().trim())
                .name(request.getName().trim())
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .build();
    }

    public void updateEntity(Unit entity, UnitUpdateRequest request) {
        entity.setCode(request.getCode().trim());
        entity.setName(request.getName().trim());
        entity.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
    }

    public UnitResponse toResponse(Unit entity) {
        return UnitResponse.builder()
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

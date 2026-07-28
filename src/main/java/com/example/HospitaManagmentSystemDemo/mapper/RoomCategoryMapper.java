package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.RoomCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.RoomCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.RoomCategoryResponse;
import com.example.HospitaManagmentSystemDemo.entity.RoomCategory;
import org.springframework.stereotype.Component;

@Component
public class RoomCategoryMapper {
    public RoomCategory toEntity(RoomCategoryCreateRequest request) {
        return RoomCategory.builder()
                .code(request.getCode().trim())
                .name(request.getName().trim())
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .defaultRate(request.getDefaultRate())
                .build();
    }

    public void updateEntity(RoomCategory entity, RoomCategoryUpdateRequest request) {
        entity.setCode(request.getCode().trim());
        entity.setName(request.getName().trim());
        entity.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
        entity.setDefaultRate(request.getDefaultRate());
    }

    public RoomCategoryResponse toResponse(RoomCategory entity) {
        return RoomCategoryResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .defaultRate(entity.getDefaultRate())
                .active(entity.getActive())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }
}

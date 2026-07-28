package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.HospitalServiceItemCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.HospitalServiceItemUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.HospitalServiceItemResponse;
import com.example.HospitaManagmentSystemDemo.entity.HospitalServiceItem;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class HospitalServiceItemMapper {
    public HospitalServiceItem toEntity(HospitalServiceItemCreateRequest request) {
        return HospitalServiceItem.builder()
                .serviceCode(request.getServiceCode().trim())
                .name(request.getName().trim())
                .description(request.getDescription() != null ? request.getDescription().trim() : null)
                .categoryId(request.getCategoryId())
                .rate(request.getRate())
                .taxPct(request.getTaxPct() != null ? request.getTaxPct() : BigDecimal.ZERO)
                .build();
    }

    public void updateEntity(HospitalServiceItem entity, HospitalServiceItemUpdateRequest request) {
        entity.setServiceCode(request.getServiceCode().trim());
        entity.setName(request.getName().trim());
        entity.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
        entity.setCategoryId(request.getCategoryId());
        entity.setRate(request.getRate());
        entity.setTaxPct(request.getTaxPct() != null ? request.getTaxPct() : BigDecimal.ZERO);
    }

    public HospitalServiceItemResponse toResponse(HospitalServiceItem entity, String categoryName) {
        return HospitalServiceItemResponse.builder()
                .id(entity.getId())
                .serviceCode(entity.getServiceCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .categoryId(entity.getCategoryId())
                .categoryName(categoryName)
                .rate(entity.getRate())
                .taxPct(entity.getTaxPct())
                .active(entity.getActive())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }
}

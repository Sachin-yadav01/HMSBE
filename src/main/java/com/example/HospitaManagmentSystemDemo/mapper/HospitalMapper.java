package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.HospitalUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.HospitalResponse;
import com.example.HospitaManagmentSystemDemo.entity.Hospital;
import org.springframework.stereotype.Component;

@Component
public class HospitalMapper {

    public void updateEntity(Hospital entity, HospitalUpdateRequest request) {
        entity.setName(request.getName().trim());
        entity.setAddressLine1(trimOrNull(request.getAddressLine1()));
        entity.setAddressLine2(trimOrNull(request.getAddressLine2()));
        entity.setCity(trimOrNull(request.getCity()));
        entity.setState(trimOrNull(request.getState()));
        entity.setPostalCode(trimOrNull(request.getPostalCode()));
        entity.setCountry(trimOrNull(request.getCountry()));
        entity.setPhonePrimary(trimOrNull(request.getPhonePrimary()));
        entity.setPhoneSecondary(trimOrNull(request.getPhoneSecondary()));
        entity.setEmail(trimOrNull(request.getEmail()));
        entity.setRegistrationNo(trimOrNull(request.getRegistrationNo()));
        entity.setGstin(trimOrNull(request.getGstin()));
        entity.setLogoUrl(trimOrNull(request.getLogoUrl()));
    }

    public HospitalResponse toResponse(Hospital entity) {
        return HospitalResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .addressLine1(entity.getAddressLine1())
                .addressLine2(entity.getAddressLine2())
                .city(entity.getCity())
                .state(entity.getState())
                .postalCode(entity.getPostalCode())
                .country(entity.getCountry())
                .phonePrimary(entity.getPhonePrimary())
                .phoneSecondary(entity.getPhoneSecondary())
                .email(entity.getEmail())
                .registrationNo(entity.getRegistrationNo())
                .gstin(entity.getGstin())
                .logoUrl(entity.getLogoUrl())
                .active(entity.getActive())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }

    private String trimOrNull(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        return value.trim();
    }
}

package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.HospitalUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.HospitalResponse;
import com.example.HospitaManagmentSystemDemo.entity.Hospital;
import com.example.HospitaManagmentSystemDemo.mapper.HospitalMapper;
import com.example.HospitaManagmentSystemDemo.repository.HospitalRepository;
import com.example.HospitaManagmentSystemDemo.service.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;
@Slf4j
@Service
public class HospitalServiceImpl implements HospitalService {

    private static final Long PROFILE_ID = 1L;
    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;

    public HospitalServiceImpl(HospitalRepository hospitalRepository, HospitalMapper hospitalMapper) {
        this.hospitalRepository = hospitalRepository;
        this.hospitalMapper = hospitalMapper;
    }

    @Override
    @Transactional
    public HospitalResponse getProfile() {
        log.info("Received getProfile request");
        return hospitalMapper.toResponse(getOrCreateProfile());
    }

    @Override
    @Transactional
    public HospitalResponse updateProfile(HospitalUpdateRequest request) {
        log.info("Received request to update hospital");
        Hospital hospital = getOrCreateProfile();
        hospitalMapper.updateEntity(hospital, request);
        log.info("Entity updated");
        return hospitalMapper.toResponse(hospitalRepository.save(hospital));
    }

    private Hospital getOrCreateProfile() {
        return hospitalRepository.findByIdAndDeletedFalse(PROFILE_ID).orElseGet(() -> {
            Hospital hospital = Hospital.builder()
                    .name("CarePulse Hospital")
                    .country("India")
                    .build();
            hospital.setActive(true);
            hospital.setDeleted(false);
            // Force id=1 via save then ensure — if empty DB without seed
            Hospital saved = hospitalRepository.save(hospital);
            return saved;
        });
    }
}

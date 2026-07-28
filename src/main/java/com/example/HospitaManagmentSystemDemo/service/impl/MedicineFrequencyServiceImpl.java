package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineFrequencyCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineFrequencyUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.MedicineFrequencyResponse;
import com.example.HospitaManagmentSystemDemo.entity.MedicineFrequency;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.MedicineFrequencyMapper;
import com.example.HospitaManagmentSystemDemo.repository.MedicineFrequencyRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.MedicineFrequencyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicineFrequencyServiceImpl implements MedicineFrequencyService {

    private final MedicineFrequencyRepository repository;
    private final MedicineFrequencyMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    public MedicineFrequencyServiceImpl(MedicineFrequencyRepository repository, MedicineFrequencyMapper mapper, CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    @Transactional
    public MedicineFrequencyResponse create(MedicineFrequencyCreateRequest request) {
        if (repository.existsByCodeIgnoreCaseAndDeletedFalse(request.getCode().trim())) {
            throw new BusinessException("MedicineFrequency code already exists", "MEDICINE_FREQUENCY_CODE_ALREADY_EXISTS");
        }
        MedicineFrequency entity = mapper.toEntity(request);
        entity.setActive(true);
        entity.setDeleted(false);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public MedicineFrequencyResponse getById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public MedicineFrequencyResponse update(Long id, MedicineFrequencyUpdateRequest request) {
        MedicineFrequency entity = getEntity(id);
        if (repository.existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(request.getCode().trim(), id)) {
            throw new BusinessException("MedicineFrequency code already exists", "MEDICINE_FREQUENCY_CODE_ALREADY_EXISTS");
        }
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public MedicineFrequencyResponse updateActivation(Long id, MasterActivationRequest request) {
        MedicineFrequency entity = getEntity(id);
        entity.setActive(request.getActive());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        MedicineFrequency entity = getEntity(id);
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(1L));
        entity.setActive(false);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MedicineFrequencyResponse> search(MasterSearchCriteria criteria, Pageable pageable) {
        Page<MedicineFrequency> page = repository.search(
                criteria.getSearchText(),
                criteria.getActive(),
                pageable
        );
        List<MedicineFrequencyResponse> content = page.getContent().stream().map(mapper::toResponse).toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicineFrequencyResponse> listActive() {
        return repository.findByActiveTrueAndDeletedFalseOrderByNameAsc()
                .stream().map(mapper::toResponse).toList();
    }

    private MedicineFrequency getEntity(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("MedicineFrequency not found with ID: " + id));
    }
}

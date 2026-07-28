package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.ConsultationTypeCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.ConsultationTypeUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.response.ConsultationTypeResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.entity.ConsultationType;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.ConsultationTypeMapper;
import com.example.HospitaManagmentSystemDemo.repository.ConsultationTypeRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.ConsultationTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultationTypeServiceImpl implements ConsultationTypeService {

    private final ConsultationTypeRepository repository;
    private final ConsultationTypeMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    public ConsultationTypeServiceImpl(ConsultationTypeRepository repository, ConsultationTypeMapper mapper,
                                       CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override @Transactional
    public ConsultationTypeResponse create(ConsultationTypeCreateRequest request) {
        if (repository.existsByCodeIgnoreCaseAndDeletedFalse(request.getCode().trim())) {
            throw new BusinessException("Consultation type code already exists", "CONSULTATION_TYPE_CODE_ALREADY_EXISTS");
        }
        ConsultationType entity = mapper.toEntity(request);
        entity.setActive(true);
        entity.setDeleted(false);
        return mapper.toResponse(repository.save(entity));
    }

    @Override @Transactional(readOnly = true)
    public ConsultationTypeResponse getById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Override @Transactional
    public ConsultationTypeResponse update(Long id, ConsultationTypeUpdateRequest request) {
        ConsultationType entity = getEntity(id);
        if (repository.existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(request.getCode().trim(), id)) {
            throw new BusinessException("Consultation type code already exists", "CONSULTATION_TYPE_CODE_ALREADY_EXISTS");
        }
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override @Transactional
    public ConsultationTypeResponse updateActivation(Long id, MasterActivationRequest request) {
        ConsultationType entity = getEntity(id);
        entity.setActive(request.getActive());
        return mapper.toResponse(repository.save(entity));
    }

    @Override @Transactional
    public void delete(Long id) {
        ConsultationType entity = getEntity(id);
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(1L));
        entity.setActive(false);
        repository.save(entity);
    }

    @Override @Transactional(readOnly = true)
    public PageResponse<ConsultationTypeResponse> search(MasterSearchCriteria criteria, Pageable pageable) {
        Page<ConsultationType> page = repository.search(criteria.getSearchText(), criteria.getActive(), pageable);
        List<ConsultationTypeResponse> content = page.getContent().stream().map(mapper::toResponse).toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override @Transactional(readOnly = true)
    public List<ConsultationTypeResponse> listActive() {
        return repository.findByActiveTrueAndDeletedFalseOrderByNameAsc().stream().map(mapper::toResponse).toList();
    }

    private ConsultationType getEntity(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Consultation type not found with ID: " + id));
    }
}

package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.SpecializationCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.SpecializationSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.SpecializationUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.SpecializationResponse;
import com.example.HospitaManagmentSystemDemo.entity.Department;
import com.example.HospitaManagmentSystemDemo.entity.Specialization;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.SpecializationMapper;
import com.example.HospitaManagmentSystemDemo.repository.DepartmentRepository;
import com.example.HospitaManagmentSystemDemo.repository.SpecializationRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.SpecializationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository repository;
    private final DepartmentRepository departmentRepository;
    private final SpecializationMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    public SpecializationServiceImpl(SpecializationRepository repository,
                                     DepartmentRepository departmentRepository,
                                     SpecializationMapper mapper,
                                     CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    @Transactional
    public SpecializationResponse create(SpecializationCreateRequest request) {
        if (repository.existsByCodeIgnoreCaseAndDeletedFalse(request.getCode().trim())) {
            throw new BusinessException("Specialization code already exists", "SPECIALIZATION_CODE_ALREADY_EXISTS");
        }
        Department department = requireActiveDepartment(request.getDepartmentId());
        Specialization entity = mapper.toEntity(request);
        entity.setActive(true);
        entity.setDeleted(false);
        Specialization saved = repository.save(entity);
        return mapper.toResponse(saved, department.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public SpecializationResponse getById(Long id) {
        Specialization entity = getEntity(id);
        return mapper.toResponse(entity, resolveDepartmentName(entity.getDepartmentId()));
    }

    @Override
    @Transactional
    public SpecializationResponse update(Long id, SpecializationUpdateRequest request) {
        Specialization entity = getEntity(id);
        if (repository.existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(request.getCode().trim(), id)) {
            throw new BusinessException("Specialization code already exists", "SPECIALIZATION_CODE_ALREADY_EXISTS");
        }
        Department department = requireActiveDepartment(request.getDepartmentId());
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity), department.getName());
    }

    @Override
    @Transactional
    public SpecializationResponse updateActivation(Long id, MasterActivationRequest request) {
        Specialization entity = getEntity(id);
        entity.setActive(request.getActive());
        return mapper.toResponse(repository.save(entity), resolveDepartmentName(entity.getDepartmentId()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Specialization entity = getEntity(id);
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(1L));
        entity.setActive(false);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SpecializationResponse> search(SpecializationSearchCriteria criteria, Pageable pageable) {
        Page<Specialization> page = repository.search(criteria.getSearchText(), criteria.getActive(),
                criteria.getDepartmentId(), pageable);
        List<SpecializationResponse> content = page.getContent().stream()
                .map(e -> mapper.toResponse(e, resolveDepartmentName(e.getDepartmentId())))
                .toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecializationResponse> listActive() {
        return repository.findByActiveTrueAndDeletedFalseOrderByNameAsc().stream()
                .map(e -> mapper.toResponse(e, resolveDepartmentName(e.getDepartmentId())))
                .toList();
    }

    private Department requireActiveDepartment(Long departmentId) {
        Department department = departmentRepository.findByIdAndDeletedFalse(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + departmentId));
        if (!Boolean.TRUE.equals(department.getActive())) {
            throw new BusinessException("Inactive department cannot be used", "INACTIVE_DEPARTMENT");
        }
        return department;
    }

    private String resolveDepartmentName(Long departmentId) {
        return departmentRepository.findByIdAndDeletedFalse(departmentId)
                .map(Department::getName).orElse(null);
    }

    private Specialization getEntity(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Specialization not found with ID: " + id));
    }
}

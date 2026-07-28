package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.DepartmentCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.DepartmentUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.DepartmentResponse;
import com.example.HospitaManagmentSystemDemo.entity.Department;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.DepartmentMapper;
import com.example.HospitaManagmentSystemDemo.repository.DepartmentRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    public DepartmentServiceImpl(DepartmentRepository repository, DepartmentMapper mapper, CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    @Transactional
    public DepartmentResponse create(DepartmentCreateRequest request) {
        if (repository.existsByCodeIgnoreCaseAndDeletedFalse(request.getCode().trim())) {
            throw new BusinessException("Department code already exists", "DEPARTMENT_CODE_ALREADY_EXISTS");
        }
        Department entity = mapper.toEntity(request);
        entity.setActive(true);
        entity.setDeleted(false);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public DepartmentResponse update(Long id, DepartmentUpdateRequest request) {
        Department entity = getEntity(id);
        if (repository.existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(request.getCode().trim(), id)) {
            throw new BusinessException("Department code already exists", "DEPARTMENT_CODE_ALREADY_EXISTS");
        }
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public DepartmentResponse updateActivation(Long id, MasterActivationRequest request) {
        Department entity = getEntity(id);
        entity.setActive(request.getActive());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Department entity = getEntity(id);
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(1L));
        entity.setActive(false);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DepartmentResponse> search(MasterSearchCriteria criteria, Pageable pageable) {
        Page<Department> page = repository.search(
                criteria.getSearchText(),
                criteria.getActive(),
                pageable
        );
        List<DepartmentResponse> content = page.getContent().stream().map(mapper::toResponse).toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> listActive() {
        return repository.findByActiveTrueAndDeletedFalseOrderByNameAsc()
                .stream().map(mapper::toResponse).toList();
    }

    private Department getEntity(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + id));
    }
}

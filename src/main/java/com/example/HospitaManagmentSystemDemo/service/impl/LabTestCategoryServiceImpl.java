package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.LabTestCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.LabTestCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.LabTestCategoryResponse;
import com.example.HospitaManagmentSystemDemo.entity.LabTestCategory;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.LabTestCategoryMapper;
import com.example.HospitaManagmentSystemDemo.repository.LabTestCategoryRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.LabTestCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LabTestCategoryServiceImpl implements LabTestCategoryService {

    private final LabTestCategoryRepository repository;
    private final LabTestCategoryMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    public LabTestCategoryServiceImpl(LabTestCategoryRepository repository, LabTestCategoryMapper mapper, CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    @Transactional
    public LabTestCategoryResponse create(LabTestCategoryCreateRequest request) {
        if (repository.existsByCodeIgnoreCaseAndDeletedFalse(request.getCode().trim())) {
            throw new BusinessException("LabTestCategory code already exists", "LAB_TEST_CATEGORY_CODE_ALREADY_EXISTS");
        }
        LabTestCategory entity = mapper.toEntity(request);
        entity.setActive(true);
        entity.setDeleted(false);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public LabTestCategoryResponse getById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public LabTestCategoryResponse update(Long id, LabTestCategoryUpdateRequest request) {
        LabTestCategory entity = getEntity(id);
        if (repository.existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(request.getCode().trim(), id)) {
            throw new BusinessException("LabTestCategory code already exists", "LAB_TEST_CATEGORY_CODE_ALREADY_EXISTS");
        }
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public LabTestCategoryResponse updateActivation(Long id, MasterActivationRequest request) {
        LabTestCategory entity = getEntity(id);
        entity.setActive(request.getActive());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LabTestCategory entity = getEntity(id);
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(1L));
        entity.setActive(false);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<LabTestCategoryResponse> search(MasterSearchCriteria criteria, Pageable pageable) {
        Page<LabTestCategory> page = repository.search(
                criteria.getSearchText(),
                criteria.getActive(),
                pageable
        );
        List<LabTestCategoryResponse> content = page.getContent().stream().map(mapper::toResponse).toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LabTestCategoryResponse> listActive() {
        return repository.findByActiveTrueAndDeletedFalseOrderByNameAsc()
                .stream().map(mapper::toResponse).toList();
    }

    private LabTestCategory getEntity(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("LabTestCategory not found with ID: " + id));
    }
}

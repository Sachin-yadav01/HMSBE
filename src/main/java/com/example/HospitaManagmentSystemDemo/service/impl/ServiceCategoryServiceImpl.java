package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.ServiceCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.ServiceCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.ServiceCategoryResponse;
import com.example.HospitaManagmentSystemDemo.entity.ServiceCategory;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.ServiceCategoryMapper;
import com.example.HospitaManagmentSystemDemo.repository.ServiceCategoryRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.ServiceCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ServiceCategoryRepository repository;
    private final ServiceCategoryMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    public ServiceCategoryServiceImpl(ServiceCategoryRepository repository, ServiceCategoryMapper mapper, CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    @Transactional
    public ServiceCategoryResponse create(ServiceCategoryCreateRequest request) {
        if (repository.existsByCodeIgnoreCaseAndDeletedFalse(request.getCode().trim())) {
            throw new BusinessException("ServiceCategory code already exists", "SERVICE_CATEGORY_CODE_ALREADY_EXISTS");
        }
        ServiceCategory entity = mapper.toEntity(request);
        entity.setActive(true);
        entity.setDeleted(false);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceCategoryResponse getById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public ServiceCategoryResponse update(Long id, ServiceCategoryUpdateRequest request) {
        ServiceCategory entity = getEntity(id);
        if (repository.existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(request.getCode().trim(), id)) {
            throw new BusinessException("ServiceCategory code already exists", "SERVICE_CATEGORY_CODE_ALREADY_EXISTS");
        }
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public ServiceCategoryResponse updateActivation(Long id, MasterActivationRequest request) {
        ServiceCategory entity = getEntity(id);
        entity.setActive(request.getActive());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ServiceCategory entity = getEntity(id);
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(1L));
        entity.setActive(false);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ServiceCategoryResponse> search(MasterSearchCriteria criteria, Pageable pageable) {
        Page<ServiceCategory> page = repository.search(
                criteria.getSearchText(),
                criteria.getActive(),
                pageable
        );
        List<ServiceCategoryResponse> content = page.getContent().stream().map(mapper::toResponse).toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceCategoryResponse> listActive() {
        return repository.findByActiveTrueAndDeletedFalseOrderByNameAsc()
                .stream().map(mapper::toResponse).toList();
    }

    private ServiceCategory getEntity(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceCategory not found with ID: " + id));
    }
}

package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.HospitalServiceItemCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.HospitalServiceItemSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.HospitalServiceItemUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.HospitalServiceItemResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.entity.HospitalServiceItem;
import com.example.HospitaManagmentSystemDemo.entity.ServiceCategory;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.HospitalServiceItemMapper;
import com.example.HospitaManagmentSystemDemo.repository.HospitalServiceItemRepository;
import com.example.HospitaManagmentSystemDemo.repository.ServiceCategoryRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.ServiceCatalogService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceCatalogServiceImpl implements ServiceCatalogService {

    private final HospitalServiceItemRepository repository;
    private final ServiceCategoryRepository categoryRepository;
    private final HospitalServiceItemMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    public ServiceCatalogServiceImpl(HospitalServiceItemRepository repository,
                                     ServiceCategoryRepository categoryRepository,
                                     HospitalServiceItemMapper mapper,
                                     CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override @Transactional
    public HospitalServiceItemResponse create(HospitalServiceItemCreateRequest request) {
        if (repository.existsByServiceCodeIgnoreCaseAndDeletedFalse(request.getServiceCode().trim())) {
            throw new BusinessException("Service code already exists", "SERVICE_CODE_ALREADY_EXISTS");
        }
        ServiceCategory category = requireActiveCategory(request.getCategoryId());
        HospitalServiceItem entity = mapper.toEntity(request);
        entity.setActive(true);
        entity.setDeleted(false);
        return mapper.toResponse(repository.save(entity), category.getName());
    }

    @Override @Transactional(readOnly = true)
    public HospitalServiceItemResponse getById(Long id) {
        HospitalServiceItem entity = getEntity(id);
        return mapper.toResponse(entity, resolveCategoryName(entity.getCategoryId()));
    }

    @Override @Transactional
    public HospitalServiceItemResponse update(Long id, HospitalServiceItemUpdateRequest request) {
        HospitalServiceItem entity = getEntity(id);
        if (repository.existsByServiceCodeIgnoreCaseAndDeletedFalseAndIdNot(request.getServiceCode().trim(), id)) {
            throw new BusinessException("Service code already exists", "SERVICE_CODE_ALREADY_EXISTS");
        }
        ServiceCategory category = requireActiveCategory(request.getCategoryId());
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity), category.getName());
    }

    @Override @Transactional
    public HospitalServiceItemResponse updateActivation(Long id, MasterActivationRequest request) {
        HospitalServiceItem entity = getEntity(id);
        entity.setActive(request.getActive());
        return mapper.toResponse(repository.save(entity), resolveCategoryName(entity.getCategoryId()));
    }

    @Override @Transactional
    public void delete(Long id) {
        HospitalServiceItem entity = getEntity(id);
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(1L));
        entity.setActive(false);
        repository.save(entity);
    }

    @Override @Transactional(readOnly = true)
    public PageResponse<HospitalServiceItemResponse> search(HospitalServiceItemSearchCriteria criteria, Pageable pageable) {
        Page<HospitalServiceItem> page = repository.search(criteria.getSearchText(), criteria.getActive(),
                criteria.getCategoryId(), pageable);
        List<HospitalServiceItemResponse> content = page.getContent().stream()
                .map(e -> mapper.toResponse(e, resolveCategoryName(e.getCategoryId())))
                .toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override @Transactional(readOnly = true)
    public List<HospitalServiceItemResponse> listActive() {
        return repository.findByActiveTrueAndDeletedFalseOrderByNameAsc().stream()
                .map(e -> mapper.toResponse(e, resolveCategoryName(e.getCategoryId())))
                .toList();
    }

    private ServiceCategory requireActiveCategory(Long categoryId) {
        ServiceCategory category = categoryRepository.findByIdAndDeletedFalse(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Service category not found with ID: " + categoryId));
        if (!Boolean.TRUE.equals(category.getActive())) {
            throw new BusinessException("Inactive service category cannot be used", "INACTIVE_SERVICE_CATEGORY");
        }
        return category;
    }

    private String resolveCategoryName(Long categoryId) {
        return categoryRepository.findByIdAndDeletedFalse(categoryId).map(ServiceCategory::getName).orElse(null);
    }

    private HospitalServiceItem getEntity(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Hospital service not found with ID: " + id));
    }
}

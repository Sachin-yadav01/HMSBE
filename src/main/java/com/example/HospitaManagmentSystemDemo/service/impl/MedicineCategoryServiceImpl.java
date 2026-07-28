package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.MedicineCategoryResponse;
import com.example.HospitaManagmentSystemDemo.entity.MedicineCategory;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.MedicineCategoryMapper;
import com.example.HospitaManagmentSystemDemo.repository.MedicineCategoryRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.MedicineCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicineCategoryServiceImpl implements MedicineCategoryService {

    private final MedicineCategoryRepository repository;
    private final MedicineCategoryMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    public MedicineCategoryServiceImpl(MedicineCategoryRepository repository, MedicineCategoryMapper mapper, CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    @Transactional
    public MedicineCategoryResponse create(MedicineCategoryCreateRequest request) {
        if (repository.existsByCodeIgnoreCaseAndDeletedFalse(request.getCode().trim())) {
            throw new BusinessException("MedicineCategory code already exists", "MEDICINE_CATEGORY_CODE_ALREADY_EXISTS");
        }
        MedicineCategory entity = mapper.toEntity(request);
        entity.setActive(true);
        entity.setDeleted(false);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public MedicineCategoryResponse getById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public MedicineCategoryResponse update(Long id, MedicineCategoryUpdateRequest request) {
        MedicineCategory entity = getEntity(id);
        if (repository.existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(request.getCode().trim(), id)) {
            throw new BusinessException("MedicineCategory code already exists", "MEDICINE_CATEGORY_CODE_ALREADY_EXISTS");
        }
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public MedicineCategoryResponse updateActivation(Long id, MasterActivationRequest request) {
        MedicineCategory entity = getEntity(id);
        entity.setActive(request.getActive());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        MedicineCategory entity = getEntity(id);
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(1L));
        entity.setActive(false);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MedicineCategoryResponse> search(MasterSearchCriteria criteria, Pageable pageable) {
        Page<MedicineCategory> page = repository.search(
                criteria.getSearchText(),
                criteria.getActive(),
                pageable
        );
        List<MedicineCategoryResponse> content = page.getContent().stream().map(mapper::toResponse).toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicineCategoryResponse> listActive() {
        return repository.findByActiveTrueAndDeletedFalseOrderByNameAsc()
                .stream().map(mapper::toResponse).toList();
    }

    private MedicineCategory getEntity(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("MedicineCategory not found with ID: " + id));
    }
}

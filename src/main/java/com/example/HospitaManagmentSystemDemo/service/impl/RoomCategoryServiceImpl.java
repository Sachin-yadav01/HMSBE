package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.RoomCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.RoomCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.RoomCategoryResponse;
import com.example.HospitaManagmentSystemDemo.entity.RoomCategory;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.RoomCategoryMapper;
import com.example.HospitaManagmentSystemDemo.repository.RoomCategoryRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.RoomCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomCategoryServiceImpl implements RoomCategoryService {

    private final RoomCategoryRepository repository;
    private final RoomCategoryMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    public RoomCategoryServiceImpl(RoomCategoryRepository repository, RoomCategoryMapper mapper,
                                   CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override @Transactional
    public RoomCategoryResponse create(RoomCategoryCreateRequest request) {
        if (repository.existsByCodeIgnoreCaseAndDeletedFalse(request.getCode().trim())) {
            throw new BusinessException("Room category code already exists", "ROOM_CATEGORY_CODE_ALREADY_EXISTS");
        }
        RoomCategory entity = mapper.toEntity(request);
        entity.setActive(true);
        entity.setDeleted(false);
        return mapper.toResponse(repository.save(entity));
    }

    @Override @Transactional(readOnly = true)
    public RoomCategoryResponse getById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Override @Transactional
    public RoomCategoryResponse update(Long id, RoomCategoryUpdateRequest request) {
        RoomCategory entity = getEntity(id);
        if (repository.existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(request.getCode().trim(), id)) {
            throw new BusinessException("Room category code already exists", "ROOM_CATEGORY_CODE_ALREADY_EXISTS");
        }
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override @Transactional
    public RoomCategoryResponse updateActivation(Long id, MasterActivationRequest request) {
        RoomCategory entity = getEntity(id);
        entity.setActive(request.getActive());
        return mapper.toResponse(repository.save(entity));
    }

    @Override @Transactional
    public void delete(Long id) {
        RoomCategory entity = getEntity(id);
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(1L));
        entity.setActive(false);
        repository.save(entity);
    }

    @Override @Transactional(readOnly = true)
    public PageResponse<RoomCategoryResponse> search(MasterSearchCriteria criteria, Pageable pageable) {
        Page<RoomCategory> page = repository.search(criteria.getSearchText(), criteria.getActive(), pageable);
        List<RoomCategoryResponse> content = page.getContent().stream().map(mapper::toResponse).toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override @Transactional(readOnly = true)
    public List<RoomCategoryResponse> listActive() {
        return repository.findByActiveTrueAndDeletedFalseOrderByNameAsc().stream().map(mapper::toResponse).toList();
    }

    private RoomCategory getEntity(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Room category not found with ID: " + id));
    }
}

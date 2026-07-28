package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.UnitCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.UnitUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.UnitResponse;
import com.example.HospitaManagmentSystemDemo.entity.Unit;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.UnitMapper;
import com.example.HospitaManagmentSystemDemo.repository.UnitRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.UnitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    private final UnitRepository repository;
    private final UnitMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    public UnitServiceImpl(UnitRepository repository, UnitMapper mapper, CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    @Transactional
    public UnitResponse create(UnitCreateRequest request) {
        if (repository.existsByCodeIgnoreCaseAndDeletedFalse(request.getCode().trim())) {
            throw new BusinessException("Unit code already exists", "UNIT_CODE_ALREADY_EXISTS");
        }
        Unit entity = mapper.toEntity(request);
        entity.setActive(true);
        entity.setDeleted(false);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public UnitResponse getById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public UnitResponse update(Long id, UnitUpdateRequest request) {
        Unit entity = getEntity(id);
        if (repository.existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(request.getCode().trim(), id)) {
            throw new BusinessException("Unit code already exists", "UNIT_CODE_ALREADY_EXISTS");
        }
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public UnitResponse updateActivation(Long id, MasterActivationRequest request) {
        Unit entity = getEntity(id);
        entity.setActive(request.getActive());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Unit entity = getEntity(id);
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(1L));
        entity.setActive(false);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UnitResponse> search(MasterSearchCriteria criteria, Pageable pageable) {
        Page<Unit> page = repository.search(
                criteria.getSearchText(),
                criteria.getActive(),
                pageable
        );
        List<UnitResponse> content = page.getContent().stream().map(mapper::toResponse).toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnitResponse> listActive() {
        return repository.findByActiveTrueAndDeletedFalseOrderByNameAsc()
                .stream().map(mapper::toResponse).toList();
    }

    private Unit getEntity(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found with ID: " + id));
    }
}

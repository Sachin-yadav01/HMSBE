package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineRouteCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineRouteUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.MedicineRouteResponse;
import com.example.HospitaManagmentSystemDemo.entity.MedicineRoute;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.MedicineRouteMapper;
import com.example.HospitaManagmentSystemDemo.repository.MedicineRouteRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.MedicineRouteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicineRouteServiceImpl implements MedicineRouteService {

    private final MedicineRouteRepository repository;
    private final MedicineRouteMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    public MedicineRouteServiceImpl(MedicineRouteRepository repository, MedicineRouteMapper mapper, CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    @Transactional
    public MedicineRouteResponse create(MedicineRouteCreateRequest request) {
        if (repository.existsByCodeIgnoreCaseAndDeletedFalse(request.getCode().trim())) {
            throw new BusinessException("MedicineRoute code already exists", "MEDICINE_ROUTE_CODE_ALREADY_EXISTS");
        }
        MedicineRoute entity = mapper.toEntity(request);
        entity.setActive(true);
        entity.setDeleted(false);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public MedicineRouteResponse getById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public MedicineRouteResponse update(Long id, MedicineRouteUpdateRequest request) {
        MedicineRoute entity = getEntity(id);
        if (repository.existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(request.getCode().trim(), id)) {
            throw new BusinessException("MedicineRoute code already exists", "MEDICINE_ROUTE_CODE_ALREADY_EXISTS");
        }
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public MedicineRouteResponse updateActivation(Long id, MasterActivationRequest request) {
        MedicineRoute entity = getEntity(id);
        entity.setActive(request.getActive());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        MedicineRoute entity = getEntity(id);
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(1L));
        entity.setActive(false);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MedicineRouteResponse> search(MasterSearchCriteria criteria, Pageable pageable) {
        Page<MedicineRoute> page = repository.search(
                criteria.getSearchText(),
                criteria.getActive(),
                pageable
        );
        List<MedicineRouteResponse> content = page.getContent().stream().map(mapper::toResponse).toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicineRouteResponse> listActive() {
        return repository.findByActiveTrueAndDeletedFalseOrderByNameAsc()
                .stream().map(mapper::toResponse).toList();
    }

    private MedicineRoute getEntity(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("MedicineRoute not found with ID: " + id));
    }
}

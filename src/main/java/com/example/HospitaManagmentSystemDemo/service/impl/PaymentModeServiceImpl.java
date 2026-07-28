package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.PaymentModeCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.PaymentModeUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PaymentModeResponse;
import com.example.HospitaManagmentSystemDemo.entity.PaymentMode;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.PaymentModeMapper;
import com.example.HospitaManagmentSystemDemo.repository.PaymentModeRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.PaymentModeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentModeServiceImpl implements PaymentModeService {

    private final PaymentModeRepository repository;
    private final PaymentModeMapper mapper;
    private final CurrentUserProvider currentUserProvider;

    public PaymentModeServiceImpl(PaymentModeRepository repository, PaymentModeMapper mapper, CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    @Transactional
    public PaymentModeResponse create(PaymentModeCreateRequest request) {
        if (repository.existsByCodeIgnoreCaseAndDeletedFalse(request.getCode().trim())) {
            throw new BusinessException("PaymentMode code already exists", "PAYMENT_MODE_CODE_ALREADY_EXISTS");
        }
        PaymentMode entity = mapper.toEntity(request);
        entity.setActive(true);
        entity.setDeleted(false);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentModeResponse getById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public PaymentModeResponse update(Long id, PaymentModeUpdateRequest request) {
        PaymentMode entity = getEntity(id);
        if (repository.existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(request.getCode().trim(), id)) {
            throw new BusinessException("PaymentMode code already exists", "PAYMENT_MODE_CODE_ALREADY_EXISTS");
        }
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public PaymentModeResponse updateActivation(Long id, MasterActivationRequest request) {
        PaymentMode entity = getEntity(id);
        entity.setActive(request.getActive());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        PaymentMode entity = getEntity(id);
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(1L));
        entity.setActive(false);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PaymentModeResponse> search(MasterSearchCriteria criteria, Pageable pageable) {
        Page<PaymentMode> page = repository.search(
                criteria.getSearchText(),
                criteria.getActive(),
                pageable
        );
        List<PaymentModeResponse> content = page.getContent().stream().map(mapper::toResponse).toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentModeResponse> listActive() {
        return repository.findByActiveTrueAndDeletedFalseOrderByNameAsc()
                .stream().map(mapper::toResponse).toList();
    }

    private PaymentMode getEntity(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("PaymentMode not found with ID: " + id));
    }
}

package com.example.HospitaManagmentSystemDemo.controller;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.SpecializationCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.SpecializationSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.SpecializationUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.ApiResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.SpecializationResponse;
import com.example.HospitaManagmentSystemDemo.service.SpecializationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/specializations")
@Tag(name = "Specializations")
public class SpecializationController {

    private static final List<String> ALLOWED_SORT_FIELDS = Arrays.asList("id", "code", "name", "createdOn");
    private final SpecializationService service;

    public SpecializationController(SpecializationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SpecializationResponse>> create(@Valid @RequestBody SpecializationCreateRequest request) {
        SpecializationResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(ApiResponse.success("Specialization created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecializationResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Specialization retrieved successfully", service.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecializationResponse>> update(@PathVariable Long id, @Valid @RequestBody SpecializationUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Specialization updated successfully", service.update(id, request)));
    }

    @PatchMapping("/{id}/activation")
    public ResponseEntity<ApiResponse<SpecializationResponse>> updateActivation(@PathVariable Long id, @Valid @RequestBody MasterActivationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Specialization activation updated successfully", service.updateActivation(id, request)));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<SpecializationResponse>>> listActive() {
        return ResponseEntity.ok(ApiResponse.success("Active specializations retrieved successfully", service.listActive()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<SpecializationResponse>>> search(
            @ModelAttribute SpecializationSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdOn") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        int safeSize = Math.min(size, 100);
        String safeSortBy = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "createdOn";
        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return ResponseEntity.ok(ApiResponse.success("Specializations retrieved successfully",
                service.search(criteria, PageRequest.of(page, safeSize, Sort.by(direction, safeSortBy)))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

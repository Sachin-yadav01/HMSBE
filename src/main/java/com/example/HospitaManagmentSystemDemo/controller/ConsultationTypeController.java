package com.example.HospitaManagmentSystemDemo.controller;

import com.example.HospitaManagmentSystemDemo.dto.request.ConsultationTypeCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.ConsultationTypeUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.response.ApiResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.ConsultationTypeResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.service.ConsultationTypeService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/consultation-types")
@Tag(name = "Consultation Types")
public class ConsultationTypeController {

    private static final List<String> ALLOWED_SORT_FIELDS = Arrays.asList("id", "code", "name", "createdOn");
    private final ConsultationTypeService service;

    public ConsultationTypeController(ConsultationTypeService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Create a consultation type")
    public ResponseEntity<ApiResponse<ConsultationTypeResponse>> create(@Valid @RequestBody ConsultationTypeCreateRequest request) {
        ConsultationTypeResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(ApiResponse.success("Consultation type created successfully", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a consultation type by ID")
    public ResponseEntity<ApiResponse<ConsultationTypeResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Consultation type retrieved successfully", service.getById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a consultation type")
    public ResponseEntity<ApiResponse<ConsultationTypeResponse>> update(@PathVariable Long id, @Valid @RequestBody ConsultationTypeUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Consultation type updated successfully", service.update(id, request)));
    }

    @PatchMapping("/{id}/activation")
    @Operation(summary = "Activate or deactivate a consultation type")
    public ResponseEntity<ApiResponse<ConsultationTypeResponse>> updateActivation(@PathVariable Long id, @Valid @RequestBody MasterActivationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Consultation type activation updated successfully", service.updateActivation(id, request)));
    }

    @GetMapping("/active")
    @Operation(summary = "List all active consultation types")
    public ResponseEntity<ApiResponse<List<ConsultationTypeResponse>>> listActive() {
        return ResponseEntity.ok(ApiResponse.success("Active consultation types retrieved successfully", service.listActive()));
    }

    @GetMapping
    @Operation(summary = "Search and filter consultation types with pagination")
    public ResponseEntity<ApiResponse<PageResponse<ConsultationTypeResponse>>> search(
            @ModelAttribute MasterSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdOn") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        int safeSize = Math.min(size, 100);
        String safeSortBy = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "createdOn";
        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return ResponseEntity.ok(ApiResponse.success("Consultation types retrieved successfully",
                service.search(criteria, PageRequest.of(page, safeSize, Sort.by(direction, safeSortBy)))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a consultation type")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

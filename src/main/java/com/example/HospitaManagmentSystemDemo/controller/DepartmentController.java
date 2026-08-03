package com.example.HospitaManagmentSystemDemo.controller;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.DepartmentCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.DepartmentUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.ApiResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.DepartmentResponse;
import com.example.HospitaManagmentSystemDemo.service.DepartmentService;
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
@RequestMapping("/api/v1/departments")
@Tag(name = "Departments")
public class DepartmentController {

    private static final List<String> ALLOWED_SORT_FIELDS = Arrays.asList("id", "code", "name", "createdOn");

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create departments")
    public ResponseEntity<ApiResponse<DepartmentResponse>> create(@Valid @RequestBody DepartmentCreateRequest request) {
        DepartmentResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location)
                .body(ApiResponse.success("Department created successfully", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a department by ID")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Department retrieved successfully", service.getById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a department")
    public ResponseEntity<ApiResponse<DepartmentResponse>> update(
            @PathVariable Long id, @Valid @RequestBody DepartmentUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Department updated successfully", service.update(id, request)));
    }

    @PatchMapping("/{id}/activation")
    @Operation(summary = "Activate or deactivate a department")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateActivation(
            @PathVariable Long id, @Valid @RequestBody MasterActivationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Department activation updated successfully",
                service.updateActivation(id, request)));
    }

    @GetMapping("/active")
    @Operation(summary = "List all active departments")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> listActive() {
        return ResponseEntity.ok(ApiResponse.success("Active departments retrieved successfully",
                service.listActive()));
    }

    @GetMapping
    @Operation(summary = "Search and filter departments with pagination")
    public ResponseEntity<ApiResponse<PageResponse<DepartmentResponse>>> search(
            @ModelAttribute MasterSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdOn") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        int safeSize = Math.min(size, 100);
        String safeSortBy = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "createdOn";
        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, safeSize, Sort.by(direction, safeSortBy));
        return ResponseEntity.ok(ApiResponse.success("Departments retrieved successfully",
                service.search(criteria, pageRequest)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a department")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.HospitaManagmentSystemDemo.controller;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.UnitCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.UnitUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.ApiResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.UnitResponse;
import com.example.HospitaManagmentSystemDemo.service.UnitService;
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
@RequestMapping("/api/v1/units")
@Tag(name = "Units")
public class UnitController {

    private static final List<String> ALLOWED_SORT_FIELDS = Arrays.asList("id", "code", "name", "createdOn");

    private final UnitService service;

    public UnitController(UnitService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create units")
    public ResponseEntity<ApiResponse<UnitResponse>> create(@Valid @RequestBody UnitCreateRequest request) {
        UnitResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location)
                .body(ApiResponse.success("Unit created successfully", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a unit by ID")
    public ResponseEntity<ApiResponse<UnitResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Unit retrieved successfully", service.getById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a unit")
    public ResponseEntity<ApiResponse<UnitResponse>> update(
            @PathVariable Long id, @Valid @RequestBody UnitUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Unit updated successfully", service.update(id, request)));
    }

    @PatchMapping("/{id}/activation")
    @Operation(summary = "Activate or deactivate a unit")
    public ResponseEntity<ApiResponse<UnitResponse>> updateActivation(
            @PathVariable Long id, @Valid @RequestBody MasterActivationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Unit activation updated successfully",
                service.updateActivation(id, request)));
    }

    @GetMapping("/active")
    @Operation(summary = "List all active units")
    public ResponseEntity<ApiResponse<List<UnitResponse>>> listActive() {
        return ResponseEntity.ok(ApiResponse.success("Active units retrieved successfully",
                service.listActive()));
    }

    @GetMapping
    @Operation(summary = "Search and filter units with pagination")
    public ResponseEntity<ApiResponse<PageResponse<UnitResponse>>> search(
            @ModelAttribute MasterSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdOn") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        int safeSize = Math.min(size, 100);
        String safeSortBy = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "createdOn";
        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, safeSize, Sort.by(direction, safeSortBy));
        return ResponseEntity.ok(ApiResponse.success("Units retrieved successfully",
                service.search(criteria, pageRequest)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a unit")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.HospitaManagmentSystemDemo.controller;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MedicineCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.ApiResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.MedicineCategoryResponse;
import com.example.HospitaManagmentSystemDemo.service.MedicineCategoryService;
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
@RequestMapping("/api/v1/medicine-categories")
@Tag(name = "Medicine Categories")
public class MedicineCategoryController {

    private static final List<String> ALLOWED_SORT_FIELDS = Arrays.asList("id", "code", "name", "createdOn");

    private final MedicineCategoryService service;

    public MedicineCategoryController(MedicineCategoryService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create medicine categories")
    public ResponseEntity<ApiResponse<MedicineCategoryResponse>> create(@Valid @RequestBody MedicineCategoryCreateRequest request) {
        MedicineCategoryResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location)
                .body(ApiResponse.success("MedicineCategory created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicineCategoryResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("MedicineCategory retrieved successfully", service.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicineCategoryResponse>> update(
            @PathVariable Long id, @Valid @RequestBody MedicineCategoryUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("MedicineCategory updated successfully", service.update(id, request)));
    }

    @PatchMapping("/{id}/activation")
    public ResponseEntity<ApiResponse<MedicineCategoryResponse>> updateActivation(
            @PathVariable Long id, @Valid @RequestBody MasterActivationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("MedicineCategory activation updated successfully",
                service.updateActivation(id, request)));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<MedicineCategoryResponse>>> listActive() {
        return ResponseEntity.ok(ApiResponse.success("Active medicine categories retrieved successfully",
                service.listActive()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<MedicineCategoryResponse>>> search(
            @ModelAttribute MasterSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdOn") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        int safeSize = Math.min(size, 100);
        String safeSortBy = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "createdOn";
        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, safeSize, Sort.by(direction, safeSortBy));
        return ResponseEntity.ok(ApiResponse.success("Medicine Categories retrieved successfully",
                service.search(criteria, pageRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

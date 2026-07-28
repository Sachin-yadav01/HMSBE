package com.example.HospitaManagmentSystemDemo.controller;

import com.example.HospitaManagmentSystemDemo.dto.request.HospitalServiceItemCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.HospitalServiceItemSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.HospitalServiceItemUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.ApiResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.HospitalServiceItemResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.service.ServiceCatalogService;
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
@RequestMapping("/api/v1/services")
@Tag(name = "Hospital Services")
public class ServiceCatalogController {

    private static final List<String> ALLOWED_SORT_FIELDS = Arrays.asList("id", "serviceCode", "name", "rate", "createdOn");
    private final ServiceCatalogService service;

    public ServiceCatalogController(ServiceCatalogService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ApiResponse<HospitalServiceItemResponse>> create(@Valid @RequestBody HospitalServiceItemCreateRequest request) {
        HospitalServiceItemResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(ApiResponse.success("Service created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HospitalServiceItemResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Service retrieved successfully", service.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HospitalServiceItemResponse>> update(@PathVariable Long id, @Valid @RequestBody HospitalServiceItemUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Service updated successfully", service.update(id, request)));
    }

    @PatchMapping("/{id}/activation")
    public ResponseEntity<ApiResponse<HospitalServiceItemResponse>> updateActivation(@PathVariable Long id, @Valid @RequestBody MasterActivationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Service activation updated successfully", service.updateActivation(id, request)));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<HospitalServiceItemResponse>>> listActive() {
        return ResponseEntity.ok(ApiResponse.success("Active services retrieved successfully", service.listActive()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<HospitalServiceItemResponse>>> search(
            @ModelAttribute HospitalServiceItemSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdOn") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        int safeSize = Math.min(size, 100);
        String safeSortBy = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "createdOn";
        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return ResponseEntity.ok(ApiResponse.success("Services retrieved successfully",
                service.search(criteria, PageRequest.of(page, safeSize, Sort.by(direction, safeSortBy)))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

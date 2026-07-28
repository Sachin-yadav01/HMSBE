package com.example.HospitaManagmentSystemDemo.controller;

import com.example.HospitaManagmentSystemDemo.dto.request.MasterActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.MasterSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.RoomCategoryCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.RoomCategoryUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.ApiResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.RoomCategoryResponse;
import com.example.HospitaManagmentSystemDemo.service.RoomCategoryService;
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
@RequestMapping("/api/v1/room-categories")
@Tag(name = "Room Categories")
public class RoomCategoryController {

    private static final List<String> ALLOWED_SORT_FIELDS = Arrays.asList("id", "code", "name", "createdOn");
    private final RoomCategoryService service;

    public RoomCategoryController(RoomCategoryService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ApiResponse<RoomCategoryResponse>> create(@Valid @RequestBody RoomCategoryCreateRequest request) {
        RoomCategoryResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(ApiResponse.success("Room category created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomCategoryResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Room category retrieved successfully", service.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomCategoryResponse>> update(@PathVariable Long id, @Valid @RequestBody RoomCategoryUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Room category updated successfully", service.update(id, request)));
    }

    @PatchMapping("/{id}/activation")
    public ResponseEntity<ApiResponse<RoomCategoryResponse>> updateActivation(@PathVariable Long id, @Valid @RequestBody MasterActivationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Room category activation updated successfully", service.updateActivation(id, request)));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<RoomCategoryResponse>>> listActive() {
        return ResponseEntity.ok(ApiResponse.success("Active room categories retrieved successfully", service.listActive()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<RoomCategoryResponse>>> search(
            @ModelAttribute MasterSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdOn") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        int safeSize = Math.min(size, 100);
        String safeSortBy = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "createdOn";
        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return ResponseEntity.ok(ApiResponse.success("Room categories retrieved successfully",
                service.search(criteria, PageRequest.of(page, safeSize, Sort.by(direction, safeSortBy)))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

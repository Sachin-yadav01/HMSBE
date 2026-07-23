package com.example.HospitaManagmentSystemDemo.controller;

import com.example.HospitaManagmentSystemDemo.dto.request.StaffActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffPatchRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffStatusUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.ApiResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.StaffResponse;
import com.example.HospitaManagmentSystemDemo.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
@Tag(name = "Staff Management", description = "APIs for managing hospital staff members")
public class StaffController {

    private final StaffService staffService;

    // Allowed sort fields to prevent SQL injection or arbitrary sorting
    private static final List<String> ALLOWED_SORT_FIELDS = Arrays.asList(
            "id", "employeeCode", "firstName", "lastName", "joiningDate", "createdOn"
    );

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('STAFF_CREATE')")
    @Operation(summary = "Register a new staff member")
    public ResponseEntity<ApiResponse<StaffResponse>> registerStaff(@Valid @RequestBody StaffCreateRequest request) {
        StaffResponse response = staffService.createStaff(request);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
                
        return ResponseEntity.created(location)
                .body(ApiResponse.success("Staff registered successfully", response));
    }

    @GetMapping("/{staffId}")
    @PreAuthorize("hasAuthority('STAFF_VIEW')")
    @Operation(summary = "Get a staff member by ID")
    public ResponseEntity<ApiResponse<StaffResponse>> getStaffById(@PathVariable Long staffId) {
        StaffResponse response = staffService.getStaffById(staffId);
        return ResponseEntity.ok(ApiResponse.success("Staff retrieved successfully", response));
    }

    @GetMapping("/employee-code/{employeeCode}")
    @PreAuthorize("hasAuthority('STAFF_VIEW')")
    @Operation(summary = "Get a staff member by Employee Code")
    public ResponseEntity<ApiResponse<StaffResponse>> getStaffByEmployeeCode(@PathVariable String employeeCode) {
        StaffResponse response = staffService.getStaffByEmployeeCode(employeeCode);
        return ResponseEntity.ok(ApiResponse.success("Staff retrieved successfully", response));
    }

    @PutMapping("/{staffId}")
    @PreAuthorize("hasAuthority('STAFF_UPDATE')")
    @Operation(summary = "Update all editable fields of a staff member")
    public ResponseEntity<ApiResponse<StaffResponse>> updateStaff(
            @PathVariable Long staffId,
            @Valid @RequestBody StaffUpdateRequest request) {
        StaffResponse response = staffService.updateStaff(staffId, request);
        return ResponseEntity.ok(ApiResponse.success("Staff updated successfully", response));
    }

    @PatchMapping("/{staffId}")
    @PreAuthorize("hasAuthority('STAFF_UPDATE')")
    @Operation(summary = "Partially update a staff member")
    public ResponseEntity<ApiResponse<StaffResponse>> patchStaff(
            @PathVariable Long staffId,
            @Valid @RequestBody StaffPatchRequest request) {
        StaffResponse response = staffService.patchStaff(staffId, request);
        return ResponseEntity.ok(ApiResponse.success("Staff partially updated successfully", response));
    }

    @PatchMapping("/{staffId}/status")
    @PreAuthorize("hasAuthority('STAFF_UPDATE')")
    @Operation(summary = "Update a staff member's status")
    public ResponseEntity<ApiResponse<StaffResponse>> updateStaffStatus(
            @PathVariable Long staffId,
            @Valid @RequestBody StaffStatusUpdateRequest request) {
        StaffResponse response = staffService.updateStaffStatus(staffId, request);
        return ResponseEntity.ok(ApiResponse.success("Staff status updated successfully", response));
    }

    @PatchMapping("/{staffId}/activation")
    @PreAuthorize("hasAuthority('STAFF_UPDATE')")
    @Operation(summary = "Activate or deactivate a staff member")
    public ResponseEntity<ApiResponse<StaffResponse>> updateStaffActivation(
            @PathVariable Long staffId,
            @Valid @RequestBody StaffActivationRequest request) {
        StaffResponse response = staffService.updateStaffActivation(staffId, request);
        return ResponseEntity.ok(ApiResponse.success("Staff activation updated successfully", response));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('STAFF_VIEW')")
    @Operation(summary = "Search and filter staff members with pagination")
    public ResponseEntity<ApiResponse<PageResponse<StaffResponse>>> searchStaff(
            @ModelAttribute StaffSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdOn") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        // Enforce max page size and allowed sort fields
        int safeSize = Math.min(size, 100);
        String safeSortBy = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "createdOn";
        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        
        PageRequest pageRequest = PageRequest.of(page, safeSize, Sort.by(direction, safeSortBy));
        
        PageResponse<StaffResponse> response = staffService.searchStaff(criteria, pageRequest);
        return ResponseEntity.ok(ApiResponse.success("Staff members retrieved successfully", response));
    }

    @DeleteMapping("/{staffId}")
    @PreAuthorize("hasAuthority('STAFF_DELETE')")
    @Operation(summary = "Soft delete a staff member")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long staffId) {
        staffService.deleteStaff(staffId);
        return ResponseEntity.noContent().build();
    }
}

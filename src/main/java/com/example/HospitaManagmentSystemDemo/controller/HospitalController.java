package com.example.HospitaManagmentSystemDemo.controller;

import com.example.HospitaManagmentSystemDemo.dto.request.HospitalUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.ApiResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.HospitalResponse;
import com.example.HospitaManagmentSystemDemo.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hospitals")
@Tag(name = "Hospital Profile")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/profile")
    @Operation(summary = "Get hospital profile")
    public ResponseEntity<ApiResponse<HospitalResponse>> getProfile() {
        return ResponseEntity.ok(ApiResponse.success("Hospital profile retrieved successfully",
                hospitalService.getProfile()));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update hospital profile")
    public ResponseEntity<ApiResponse<HospitalResponse>> updateProfile(
            @Valid @RequestBody HospitalUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Hospital profile updated successfully",
                hospitalService.updateProfile(request)));
    }
}

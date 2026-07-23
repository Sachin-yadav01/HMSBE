package com.example.HospitaManagmentSystemDemo.mapper;

import com.example.HospitaManagmentSystemDemo.dto.request.StaffCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffPatchRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.StaffResponse;
import com.example.HospitaManagmentSystemDemo.entity.Staff;
import org.springframework.stereotype.Component;

@Component
public class StaffMapper {

    public Staff toEntity(StaffCreateRequest request) {
        if (request == null) {
            return null;
        }

        Staff staff = new Staff();
        staff.setStaffType(request.getStaffType());
        staff.setTitle(request.getTitle());
        staff.setFirstName(request.getFirstName());
        staff.setMiddleName(request.getMiddleName());
        staff.setLastName(request.getLastName());
        staff.setGender(request.getGender());
        staff.setDateOfBirth(request.getDateOfBirth());
        staff.setProfileImageUrl(request.getProfileImageUrl());
        staff.setPhonePrimary(request.getPhonePrimary());
        staff.setPhoneSecondary(request.getPhoneSecondary());
        staff.setEmail(request.getEmail());
        staff.setEmergencyContactName(request.getEmergencyContactName());
        staff.setEmergencyContactPhone(request.getEmergencyContactPhone());
        staff.setAddressLine1(request.getAddressLine1());
        staff.setAddressLine2(request.getAddressLine2());
        staff.setCity(request.getCity());
        staff.setDistrict(request.getDistrict());
        staff.setState(request.getState());
        staff.setPostalCode(request.getPostalCode());
        staff.setCountry(request.getCountry());
        staff.setDepartmentId(request.getDepartmentId());
        staff.setDesignationId(request.getDesignationId());
        staff.setEmploymentType(request.getEmploymentType());
        staff.setJoiningDate(request.getJoiningDate());
        staff.setProbationEndDate(request.getProbationEndDate());
        staff.setReportingManagerId(request.getReportingManagerId());
        staff.setShiftId(request.getShiftId());
        staff.setWeeklyOffDay(request.getWeeklyOffDay());
        staff.setQualification(request.getQualification());
        staff.setSpecialization(request.getSpecialization());
        staff.setExperienceYears(request.getExperienceYears());
        staff.setRegistrationNumber(request.getRegistrationNumber());
        staff.setRegistrationCouncil(request.getRegistrationCouncil());
        staff.setRegistrationExpiryDate(request.getRegistrationExpiryDate());

        return staff;
    }

    public void updateEntity(Staff staff, StaffUpdateRequest request) {
        if (request == null || staff == null) {
            return;
        }

        staff.setStaffType(request.getStaffType());
        staff.setTitle(request.getTitle());
        staff.setFirstName(request.getFirstName());
        staff.setMiddleName(request.getMiddleName());
        staff.setLastName(request.getLastName());
        staff.setGender(request.getGender());
        staff.setDateOfBirth(request.getDateOfBirth());
        staff.setProfileImageUrl(request.getProfileImageUrl());
        staff.setPhonePrimary(request.getPhonePrimary());
        staff.setPhoneSecondary(request.getPhoneSecondary());
        staff.setEmail(request.getEmail());
        staff.setEmergencyContactName(request.getEmergencyContactName());
        staff.setEmergencyContactPhone(request.getEmergencyContactPhone());
        staff.setAddressLine1(request.getAddressLine1());
        staff.setAddressLine2(request.getAddressLine2());
        staff.setCity(request.getCity());
        staff.setDistrict(request.getDistrict());
        staff.setState(request.getState());
        staff.setPostalCode(request.getPostalCode());
        staff.setCountry(request.getCountry());
        staff.setDepartmentId(request.getDepartmentId());
        staff.setDesignationId(request.getDesignationId());
        staff.setEmploymentType(request.getEmploymentType());
        staff.setJoiningDate(request.getJoiningDate());
        staff.setProbationEndDate(request.getProbationEndDate());
        staff.setReportingManagerId(request.getReportingManagerId());
        staff.setShiftId(request.getShiftId());
        staff.setWeeklyOffDay(request.getWeeklyOffDay());
        staff.setQualification(request.getQualification());
        staff.setSpecialization(request.getSpecialization());
        staff.setExperienceYears(request.getExperienceYears());
        staff.setRegistrationNumber(request.getRegistrationNumber());
        staff.setRegistrationCouncil(request.getRegistrationCouncil());
        staff.setRegistrationExpiryDate(request.getRegistrationExpiryDate());
    }

    public void patchEntity(Staff staff, StaffPatchRequest request) {
        if (request == null || staff == null) {
            return;
        }

        if (request.getStaffType() != null) staff.setStaffType(request.getStaffType());
        if (request.getTitle() != null) staff.setTitle(request.getTitle());
        if (request.getFirstName() != null) staff.setFirstName(request.getFirstName());
        if (request.getMiddleName() != null) staff.setMiddleName(request.getMiddleName());
        if (request.getLastName() != null) staff.setLastName(request.getLastName());
        if (request.getGender() != null) staff.setGender(request.getGender());
        if (request.getDateOfBirth() != null) staff.setDateOfBirth(request.getDateOfBirth());
        if (request.getProfileImageUrl() != null) staff.setProfileImageUrl(request.getProfileImageUrl());
        if (request.getPhonePrimary() != null) staff.setPhonePrimary(request.getPhonePrimary());
        if (request.getPhoneSecondary() != null) staff.setPhoneSecondary(request.getPhoneSecondary());
        if (request.getEmail() != null) staff.setEmail(request.getEmail());
        if (request.getEmergencyContactName() != null) staff.setEmergencyContactName(request.getEmergencyContactName());
        if (request.getEmergencyContactPhone() != null) staff.setEmergencyContactPhone(request.getEmergencyContactPhone());
        if (request.getAddressLine1() != null) staff.setAddressLine1(request.getAddressLine1());
        if (request.getAddressLine2() != null) staff.setAddressLine2(request.getAddressLine2());
        if (request.getCity() != null) staff.setCity(request.getCity());
        if (request.getDistrict() != null) staff.setDistrict(request.getDistrict());
        if (request.getState() != null) staff.setState(request.getState());
        if (request.getPostalCode() != null) staff.setPostalCode(request.getPostalCode());
        if (request.getCountry() != null) staff.setCountry(request.getCountry());
        if (request.getDepartmentId() != null) staff.setDepartmentId(request.getDepartmentId());
        if (request.getDesignationId() != null) staff.setDesignationId(request.getDesignationId());
        if (request.getEmploymentType() != null) staff.setEmploymentType(request.getEmploymentType());
        if (request.getJoiningDate() != null) staff.setJoiningDate(request.getJoiningDate());
        if (request.getProbationEndDate() != null) staff.setProbationEndDate(request.getProbationEndDate());
        if (request.getReportingManagerId() != null) staff.setReportingManagerId(request.getReportingManagerId());
        if (request.getShiftId() != null) staff.setShiftId(request.getShiftId());
        if (request.getWeeklyOffDay() != null) staff.setWeeklyOffDay(request.getWeeklyOffDay());
        if (request.getQualification() != null) staff.setQualification(request.getQualification());
        if (request.getSpecialization() != null) staff.setSpecialization(request.getSpecialization());
        if (request.getExperienceYears() != null) staff.setExperienceYears(request.getExperienceYears());
        if (request.getRegistrationNumber() != null) staff.setRegistrationNumber(request.getRegistrationNumber());
        if (request.getRegistrationCouncil() != null) staff.setRegistrationCouncil(request.getRegistrationCouncil());
        if (request.getRegistrationExpiryDate() != null) staff.setRegistrationExpiryDate(request.getRegistrationExpiryDate());
    }

    public StaffResponse toResponse(Staff staff) {
        if (staff == null) {
            return null;
        }

        StaffResponse response = new StaffResponse();
        response.setId(staff.getId());
        response.setStaffUuid(staff.getStaffUuid());
        response.setEmployeeCode(staff.getEmployeeCode());
        
        // Safely build full name
        StringBuilder fullName = new StringBuilder();
        if (staff.getFirstName() != null) fullName.append(staff.getFirstName());
        if (staff.getMiddleName() != null && !staff.getMiddleName().trim().isEmpty()) {
            fullName.append(" ").append(staff.getMiddleName());
        }
        if (staff.getLastName() != null) fullName.append(" ").append(staff.getLastName());
        response.setFullName(fullName.toString().trim());

        response.setTitle(staff.getTitle());
        response.setFirstName(staff.getFirstName());
        response.setMiddleName(staff.getMiddleName());
        response.setLastName(staff.getLastName());
        response.setStaffType(staff.getStaffType());
        response.setGender(staff.getGender());
        response.setDateOfBirth(staff.getDateOfBirth());
        response.setPhonePrimary(staff.getPhonePrimary());
        response.setPhoneSecondary(staff.getPhoneSecondary());
        response.setEmail(staff.getEmail());
        response.setDepartmentId(staff.getDepartmentId());
        response.setDesignationId(staff.getDesignationId());
        response.setEmploymentType(staff.getEmploymentType());
        response.setJoiningDate(staff.getJoiningDate());
        response.setReportingManagerId(staff.getReportingManagerId());
        response.setShiftId(staff.getShiftId());
        response.setWeeklyOffDay(staff.getWeeklyOffDay());
        response.setStatus(staff.getStatus());
        response.setIsActive(staff.getIsActive());
        response.setQualification(staff.getQualification());
        response.setSpecialization(staff.getSpecialization());
        response.setExperienceYears(staff.getExperienceYears());
        response.setRegistrationNumber(staff.getRegistrationNumber());
        response.setRegistrationCouncil(staff.getRegistrationCouncil());
        response.setRegistrationExpiryDate(staff.getRegistrationExpiryDate());
        response.setProfileImageUrl(staff.getProfileImageUrl());
        response.setCreatedOn(staff.getCreatedOn());
        response.setUpdatedOn(staff.getUpdatedOn());

        return response;
    }
}

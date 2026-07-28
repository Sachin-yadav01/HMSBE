package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.constant.StaffStatus;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffActivationRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffPatchRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffSearchCriteria;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffStatusUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffUpdateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.PageResponse;
import com.example.HospitaManagmentSystemDemo.dto.response.StaffResponse;
import com.example.HospitaManagmentSystemDemo.entity.Department;
import com.example.HospitaManagmentSystemDemo.entity.Staff;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.StaffMapper;
import com.example.HospitaManagmentSystemDemo.repository.DepartmentRepository;
import com.example.HospitaManagmentSystemDemo.repository.StaffRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.EmployeeCodeGenerator;
import com.example.HospitaManagmentSystemDemo.service.StaffService;
import com.example.HospitaManagmentSystemDemo.StaffSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Service
public class StaffServiceImpl implements StaffService {

    private static final Logger log = LoggerFactory.getLogger(StaffServiceImpl.class);

    private final StaffRepository staffRepository;
    private final DepartmentRepository departmentRepository;
    private final StaffMapper staffMapper;
    private final EmployeeCodeGenerator codeGenerator;
    private final CurrentUserProvider currentUserProvider;
    private final int minWorkingAge;

    public StaffServiceImpl(
            StaffRepository staffRepository,
            DepartmentRepository departmentRepository,
            StaffMapper staffMapper,
            EmployeeCodeGenerator codeGenerator,
            CurrentUserProvider currentUserProvider,
            @Value("${hms.staff.minimum-working-age:18}") int minWorkingAge) {
        this.staffRepository = staffRepository;
        this.departmentRepository = departmentRepository;
        this.staffMapper = staffMapper;
        this.codeGenerator = codeGenerator;
        this.currentUserProvider = currentUserProvider;
        this.minWorkingAge = minWorkingAge;
    }

    @Override
    @Transactional
    public StaffResponse createStaff(StaffCreateRequest request) {
        validateAge(request.getDateOfBirth());
        validateDuplicateForCreate(request);
        validateBusinessRules(request.getPhonePrimary(), request.getPhoneSecondary(), request.getJoiningDate(), request.getRegistrationExpiryDate(), request.getReportingManagerId(), null);

        Staff staff = staffMapper.toEntity(request);
        staff.setEmployeeCode(codeGenerator.generateCode());
        staff.setStatus(StaffStatus.ACTIVE);
        staff.setIsActive(true);
        staff.setDeleted(false);

        Staff savedStaff = staffRepository.save(staff);
        log.info("Staff registered successfully. staffId={}, employeeCode={}", savedStaff.getId(), savedStaff.getEmployeeCode());

        return enrichResponse(staffMapper.toResponse(savedStaff));
    }

    @Override
    @Transactional(readOnly = true)
    public StaffResponse getStaffById(Long staffId) {
        return staffRepository.findByIdAndDeletedFalse(staffId)
                .map(staffMapper::toResponse)
                .map(this::enrichResponse)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + staffId));
    }

    @Override
    @Transactional(readOnly = true)
    public StaffResponse getStaffByEmployeeCode(String employeeCode) {
        return staffRepository.findByEmployeeCodeAndDeletedFalse(employeeCode)
                .map(staffMapper::toResponse)
                .map(this::enrichResponse)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found with Employee Code: " + employeeCode));
    }

    @Override
    @Transactional
    public StaffResponse updateStaff(Long staffId, StaffUpdateRequest request) {
        Staff staff = getStaffEntity(staffId);
        
        validateAge(request.getDateOfBirth());
        validateDuplicateForUpdate(request, staffId);
        validateBusinessRules(request.getPhonePrimary(), request.getPhoneSecondary(), request.getJoiningDate(), request.getRegistrationExpiryDate(), request.getReportingManagerId(), staffId);

        staffMapper.updateEntity(staff, request);
        Staff updatedStaff = staffRepository.save(staff);
        log.info("Staff updated successfully. staffId={}", updatedStaff.getId());

        return enrichResponse(staffMapper.toResponse(updatedStaff));
    }

    @Override
    @Transactional
    public StaffResponse patchStaff(Long staffId, StaffPatchRequest request) {
        Staff staff = getStaffEntity(staffId);
        
        if (request.getDateOfBirth() != null) {
            validateAge(request.getDateOfBirth());
        }
        
        // Handle conditional duplicate checks for patch
        String emailToCheck = request.getEmail() != null ? request.getEmail() : staff.getEmail();
        String phonePrimaryToCheck = request.getPhonePrimary() != null ? request.getPhonePrimary() : staff.getPhonePrimary();
        String regNumToCheck = request.getRegistrationNumber() != null ? request.getRegistrationNumber() : staff.getRegistrationNumber();
        
        if (staffRepository.existsByEmailIgnoreCaseAndDeletedFalseAndIdNot(emailToCheck, staffId)) {
            throw new BusinessException("Email already exists", "STAFF_EMAIL_ALREADY_EXISTS");
        }
        if (staffRepository.existsByPhonePrimaryAndDeletedFalseAndIdNot(phonePrimaryToCheck, staffId)) {
            throw new BusinessException("Primary phone number already exists", "STAFF_PHONE_ALREADY_EXISTS");
        }
        if (regNumToCheck != null && !regNumToCheck.isEmpty() && staffRepository.existsByRegistrationNumberIgnoreCaseAndDeletedFalseAndIdNot(regNumToCheck, staffId)) {
            throw new BusinessException("Registration number already exists", "STAFF_REGISTRATION_NUMBER_ALREADY_EXISTS");
        }
        
        String phoneSecToCheck = request.getPhoneSecondary() != null ? request.getPhoneSecondary() : staff.getPhoneSecondary();
        LocalDate joiningDateToCheck = request.getJoiningDate() != null ? request.getJoiningDate() : staff.getJoiningDate();
        LocalDate regExpToCheck = request.getRegistrationExpiryDate() != null ? request.getRegistrationExpiryDate() : staff.getRegistrationExpiryDate();
        Long reportingMgrToCheck = request.getReportingManagerId() != null ? request.getReportingManagerId() : staff.getReportingManagerId();
        
        validateBusinessRules(phonePrimaryToCheck, phoneSecToCheck, joiningDateToCheck, regExpToCheck, reportingMgrToCheck, staffId);

        staffMapper.patchEntity(staff, request);
        Staff updatedStaff = staffRepository.save(staff);
        log.info("Staff partially updated successfully. staffId={}", updatedStaff.getId());

        return enrichResponse(staffMapper.toResponse(updatedStaff));
    }

    @Override
    @Transactional
    public StaffResponse updateStaffStatus(Long staffId, StaffStatusUpdateRequest request) {
        Staff staff = getStaffEntity(staffId);
        
        // Basic status transition validation (simplified for example)
        if (staff.getStatus() == StaffStatus.RESIGNED || staff.getStatus() == StaffStatus.TERMINATED) {
            throw new BusinessException("Cannot change status of resigned or terminated staff", "INVALID_STATUS_TRANSITION");
        }

        staff.setStatus(request.getStatus());
        // Sync active status based on specific conditions
        if (request.getStatus() == StaffStatus.ACTIVE) {
            staff.setIsActive(true);
        } else if (request.getStatus() == StaffStatus.INACTIVE || request.getStatus() == StaffStatus.SUSPENDED) {
            staff.setIsActive(false);
        }
        
        Staff updatedStaff = staffRepository.save(staff);
        log.info("Staff status updated successfully. staffId={}, newStatus={}", updatedStaff.getId(), request.getStatus());
        return enrichResponse(staffMapper.toResponse(updatedStaff));
    }

    @Override
    @Transactional
    public StaffResponse updateStaffActivation(Long staffId, StaffActivationRequest request) {
        Staff staff = getStaffEntity(staffId);
        
        if (staff.getStatus() == StaffStatus.RESIGNED || staff.getStatus() == StaffStatus.TERMINATED) {
            throw new BusinessException("Cannot activate resigned or terminated staff", "INVALID_ACTIVATION");
        }
        
        staff.setIsActive(request.getActive());
        if (request.getActive() && staff.getStatus() != StaffStatus.ACTIVE) {
            staff.setStatus(StaffStatus.ACTIVE);
        } else if (!request.getActive() && staff.getStatus() == StaffStatus.ACTIVE) {
            staff.setStatus(StaffStatus.INACTIVE);
        }

        Staff updatedStaff = staffRepository.save(staff);
        log.info("Staff activation updated successfully. staffId={}, isActive={}", updatedStaff.getId(), request.getActive());
        return enrichResponse(staffMapper.toResponse(updatedStaff));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<StaffResponse> searchStaff(StaffSearchCriteria criteria, Pageable pageable) {
        Page<Staff> page = staffRepository.findAll(StaffSpecification.buildSpecification(criteria), pageable);
        return new PageResponse<>(
                page.getContent().stream().map(staffMapper::toResponse).map(this::enrichResponse).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    @Override
    @Transactional
    public void deleteStaff(Long staffId) {
        Staff staff = getStaffEntity(staffId);
        
        // TODO: Validate blocking assignments (appointments, tasks, etc.)
        
        staff.setDeleted(true);
        staff.setDeletedAt(LocalDateTime.now());
        staff.setDeletedBy(currentUserProvider.getCurrentUserId().orElse(null));
        staff.setIsActive(false);
        staffRepository.save(staff);
        
        log.info("Staff soft-deleted successfully. staffId={}", staffId);
    }

    private Staff getStaffEntity(Long staffId) {
        return staffRepository.findByIdAndDeletedFalse(staffId)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + staffId));
    }

    private StaffResponse enrichResponse(StaffResponse response) {
        if (response == null || response.getDepartmentId() == null) {
            return response;
        }
        departmentRepository.findByIdAndDeletedFalse(response.getDepartmentId())
                .map(Department::getName)
                .ifPresent(response::setDepartmentName);
        return response;
    }

    private void validateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) return;
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        if (age < minWorkingAge) {
            throw new BusinessException("Staff must be at least " + minWorkingAge + " years old", "UNDERAGE_STAFF");
        }
    }

    private void validateDuplicateForCreate(StaffCreateRequest request) {
        if (staffRepository.existsByEmailIgnoreCaseAndDeletedFalse(request.getEmail())) {
            throw new BusinessException("Email already exists", "STAFF_EMAIL_ALREADY_EXISTS");
        }
        if (staffRepository.existsByPhonePrimaryAndDeletedFalse(request.getPhonePrimary())) {
            throw new BusinessException("Primary phone number already exists", "STAFF_PHONE_ALREADY_EXISTS");
        }
        if (request.getRegistrationNumber() != null && !request.getRegistrationNumber().isEmpty() &&
                staffRepository.existsByRegistrationNumberIgnoreCaseAndDeletedFalse(request.getRegistrationNumber())) {
            throw new BusinessException("Registration number already exists", "STAFF_REGISTRATION_NUMBER_ALREADY_EXISTS");
        }
    }

    private void validateDuplicateForUpdate(StaffUpdateRequest request, Long staffId) {
        if (staffRepository.existsByEmailIgnoreCaseAndDeletedFalseAndIdNot(request.getEmail(), staffId)) {
            throw new BusinessException("Email already exists", "STAFF_EMAIL_ALREADY_EXISTS");
        }
        if (staffRepository.existsByPhonePrimaryAndDeletedFalseAndIdNot(request.getPhonePrimary(), staffId)) {
            throw new BusinessException("Primary phone number already exists", "STAFF_PHONE_ALREADY_EXISTS");
        }
        if (request.getRegistrationNumber() != null && !request.getRegistrationNumber().isEmpty() &&
                staffRepository.existsByRegistrationNumberIgnoreCaseAndDeletedFalseAndIdNot(request.getRegistrationNumber(), staffId)) {
            throw new BusinessException("Registration number already exists", "STAFF_REGISTRATION_NUMBER_ALREADY_EXISTS");
        }
    }
    
    private void validateBusinessRules(String phonePrimary, String phoneSecondary, LocalDate joiningDate, LocalDate regExpiryDate, Long reportingManagerId, Long currentStaffId) {
        if (phonePrimary != null && phonePrimary.equals(phoneSecondary)) {
            throw new BusinessException("Primary and secondary phone numbers must not be identical", "IDENTICAL_PHONE_NUMBERS");
        }
        if (regExpiryDate != null && joiningDate != null && regExpiryDate.isBefore(joiningDate)) {
            throw new BusinessException("Registration expiry date cannot be before joining date", "INVALID_REGISTRATION_DATE");
        }
        if (reportingManagerId != null && currentStaffId != null && reportingManagerId.equals(currentStaffId)) {
            throw new BusinessException("Staff cannot report to themselves", "SELF_REPORTING_MANAGER");
        }
        // Additional checks like checking if dept/designation/manager exist can be added here
    }
}

package com.example.HospitaManagmentSystemDemo.service.impl;

import com.example.HospitaManagmentSystemDemo.constant.EmploymentType;
import com.example.HospitaManagmentSystemDemo.constant.Gender;
import com.example.HospitaManagmentSystemDemo.constant.StaffType;
import com.example.HospitaManagmentSystemDemo.constant.Title;
import com.example.HospitaManagmentSystemDemo.dto.request.StaffCreateRequest;
import com.example.HospitaManagmentSystemDemo.dto.response.StaffResponse;
import com.example.HospitaManagmentSystemDemo.entity.Staff;
import com.example.HospitaManagmentSystemDemo.exception.BusinessException;
import com.example.HospitaManagmentSystemDemo.mapper.StaffMapper;
import com.example.HospitaManagmentSystemDemo.repository.StaffRepository;
import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import com.example.HospitaManagmentSystemDemo.service.EmployeeCodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StaffServiceImplTest {

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private StaffMapper staffMapper;

    @Mock
    private EmployeeCodeGenerator codeGenerator;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @InjectMocks
    private StaffServiceImpl staffService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(staffService, "minWorkingAge", 18);
    }

    @Test
    void testCreateStaff_Success() {
        StaffCreateRequest request = createMockRequest();
        Staff staff = new Staff();
        staff.setId(1L);

        StaffResponse mockResponse = new StaffResponse();
        mockResponse.setId(1L);

        when(staffRepository.existsByEmailIgnoreCaseAndDeletedFalse(request.getEmail())).thenReturn(false);
        when(staffRepository.existsByPhonePrimaryAndDeletedFalse(request.getPhonePrimary())).thenReturn(false);
        when(staffMapper.toEntity(request)).thenReturn(staff);
        when(codeGenerator.generateCode()).thenReturn("HMS-STF-000001");
        when(staffRepository.save(any(Staff.class))).thenReturn(staff);
        when(staffMapper.toResponse(staff)).thenReturn(mockResponse);

        StaffResponse response = staffService.createStaff(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(staffRepository, times(1)).save(any(Staff.class));
    }

    @Test
    void testCreateStaff_UnderageRejection() {
        StaffCreateRequest request = createMockRequest();
        request.setDateOfBirth(LocalDate.now().minusYears(15)); // Under 18

        BusinessException exception = assertThrows(BusinessException.class, () -> staffService.createStaff(request));
        assertEquals("UNDERAGE_STAFF", exception.getErrorCode());
    }

    @Test
    void testCreateStaff_DuplicateEmailRejection() {
        StaffCreateRequest request = createMockRequest();

        when(staffRepository.existsByEmailIgnoreCaseAndDeletedFalse(request.getEmail())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> staffService.createStaff(request));
        assertEquals("STAFF_EMAIL_ALREADY_EXISTS", exception.getErrorCode());
    }

    @Test
    void testCreateStaff_DuplicatePhoneRejection() {
        StaffCreateRequest request = createMockRequest();

        when(staffRepository.existsByEmailIgnoreCaseAndDeletedFalse(request.getEmail())).thenReturn(false);
        when(staffRepository.existsByPhonePrimaryAndDeletedFalse(request.getPhonePrimary())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> staffService.createStaff(request));
        assertEquals("STAFF_PHONE_ALREADY_EXISTS", exception.getErrorCode());
    }

    @Test
    void testCreateStaff_IdenticalPhonesRejection() {
        StaffCreateRequest request = createMockRequest();
        request.setPhoneSecondary(request.getPhonePrimary()); // identical

        when(staffRepository.existsByEmailIgnoreCaseAndDeletedFalse(request.getEmail())).thenReturn(false);
        when(staffRepository.existsByPhonePrimaryAndDeletedFalse(request.getPhonePrimary())).thenReturn(false);

        BusinessException exception = assertThrows(BusinessException.class, () -> staffService.createStaff(request));
        assertEquals("IDENTICAL_PHONE_NUMBERS", exception.getErrorCode());
    }

    @Test
    void testGetStaffById_Success() {
        Staff staff = new Staff();
        staff.setId(1L);
        StaffResponse mockResponse = new StaffResponse();
        mockResponse.setId(1L);

        when(staffRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(staff));
        when(staffMapper.toResponse(staff)).thenReturn(mockResponse);

        StaffResponse response = staffService.getStaffById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    private StaffCreateRequest createMockRequest() {
        StaffCreateRequest request = new StaffCreateRequest();
        request.setStaffType(StaffType.DOCTOR);
        request.setTitle(Title.DR);
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setGender(Gender.MALE);
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setPhonePrimary("1234567890");
        request.setPhoneSecondary("0987654321");
        request.setEmail("john.doe@example.com");
        request.setAddressLine1("123 Main St");
        request.setCity("Metropolis");
        request.setState("State");
        request.setPostalCode("12345");
        request.setCountry("Country");
        request.setDepartmentId(1L);
        request.setDesignationId(1L);
        request.setEmploymentType(EmploymentType.PERMANENT);
        request.setJoiningDate(LocalDate.now());
        return request;
    }
}

package com.example.HospitaManagmentSystemDemo.repository;

import com.example.HospitaManagmentSystemDemo.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long>, JpaSpecificationExecutor<Staff> {

    Optional<Staff> findByIdAndDeletedFalse(Long id);

    Optional<Staff> findByEmployeeCodeAndDeletedFalse(String employeeCode);

    boolean existsByEmailIgnoreCaseAndDeletedFalse(String email);
    
    boolean existsByEmailIgnoreCaseAndDeletedFalseAndIdNot(String email, Long id);

    boolean existsByPhonePrimaryAndDeletedFalse(String phonePrimary);
    
    boolean existsByPhonePrimaryAndDeletedFalseAndIdNot(String phonePrimary, Long id);

    boolean existsByRegistrationNumberIgnoreCaseAndDeletedFalse(String registrationNumber);
    
    boolean existsByRegistrationNumberIgnoreCaseAndDeletedFalseAndIdNot(String registrationNumber, Long id);

}

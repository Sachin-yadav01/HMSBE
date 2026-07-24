package com.example.HospitaManagmentSystemDemo.repository;

import com.example.HospitaManagmentSystemDemo.entity.EmployeeCodeSequence;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeCodeSequenceRepository extends JpaRepository<EmployeeCodeSequence, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<EmployeeCodeSequence> findFirstByOrderByIdAsc();
}

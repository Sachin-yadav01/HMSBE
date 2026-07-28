package com.example.HospitaManagmentSystemDemo.repository;

import com.example.HospitaManagmentSystemDemo.entity.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    Optional<Specialization> findByIdAndDeletedFalse(Long id);
    boolean existsByCodeIgnoreCaseAndDeletedFalse(String code);
    boolean existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(String code, Long id);
    List<Specialization> findByActiveTrueAndDeletedFalseOrderByNameAsc();

    @Query("""
        SELECT e FROM Specialization e
        WHERE e.deleted = false
          AND (:active IS NULL OR e.active = :active)
          AND (:departmentId IS NULL OR e.departmentId = :departmentId)
          AND (
            :searchText IS NULL OR :searchText = ''
            OR LOWER(e.code) LIKE LOWER(CONCAT('%', :searchText, '%'))
            OR LOWER(e.name) LIKE LOWER(CONCAT('%', :searchText, '%'))
          )
        """)
    Page<Specialization> search(@Param("searchText") String searchText,
                                @Param("active") Boolean active,
                                @Param("departmentId") Long departmentId,
                                Pageable pageable);
}

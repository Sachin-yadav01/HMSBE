package com.example.HospitaManagmentSystemDemo.repository;

import com.example.HospitaManagmentSystemDemo.entity.ConsultationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConsultationTypeRepository extends JpaRepository<ConsultationType, Long> {
    Optional<ConsultationType> findByIdAndDeletedFalse(Long id);
    boolean existsByCodeIgnoreCaseAndDeletedFalse(String code);
    boolean existsByCodeIgnoreCaseAndDeletedFalseAndIdNot(String code, Long id);
    List<ConsultationType> findByActiveTrueAndDeletedFalseOrderByNameAsc();

    @Query("""
        SELECT e FROM ConsultationType e
        WHERE e.deleted = false
          AND (:active IS NULL OR e.active = :active)
          AND (
            :searchText IS NULL OR :searchText = ''
            OR LOWER(e.code) LIKE LOWER(CONCAT('%', :searchText, '%'))
            OR LOWER(e.name) LIKE LOWER(CONCAT('%', :searchText, '%'))
          )
        """)
    Page<ConsultationType> search(@Param("searchText") String searchText,
                                   @Param("active") Boolean active,
                                   Pageable pageable);
}

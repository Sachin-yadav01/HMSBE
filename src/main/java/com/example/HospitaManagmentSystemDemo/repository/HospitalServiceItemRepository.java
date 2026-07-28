package com.example.HospitaManagmentSystemDemo.repository;

import com.example.HospitaManagmentSystemDemo.entity.HospitalServiceItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HospitalServiceItemRepository extends JpaRepository<HospitalServiceItem, Long> {
    Optional<HospitalServiceItem> findByIdAndDeletedFalse(Long id);
    boolean existsByServiceCodeIgnoreCaseAndDeletedFalse(String serviceCode);
    boolean existsByServiceCodeIgnoreCaseAndDeletedFalseAndIdNot(String serviceCode, Long id);
    List<HospitalServiceItem> findByActiveTrueAndDeletedFalseOrderByNameAsc();

    @Query("""
        SELECT e FROM HospitalServiceItem e
        WHERE e.deleted = false
          AND (:active IS NULL OR e.active = :active)
          AND (:categoryId IS NULL OR e.categoryId = :categoryId)
          AND (
            :searchText IS NULL OR :searchText = ''
            OR LOWER(e.serviceCode) LIKE LOWER(CONCAT('%', :searchText, '%'))
            OR LOWER(e.name) LIKE LOWER(CONCAT('%', :searchText, '%'))
          )
        """)
    Page<HospitalServiceItem> search(@Param("searchText") String searchText,
                                     @Param("active") Boolean active,
                                     @Param("categoryId") Long categoryId,
                                     Pageable pageable);
}

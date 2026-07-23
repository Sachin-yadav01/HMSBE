package com.example.HospitaManagmentSystemDemo;


import com.example.HospitaManagmentSystemDemo.dto.request.StaffSearchCriteria;
import com.example.HospitaManagmentSystemDemo.entity.Staff;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StaffSpecification {

    public static Specification<Staff> buildSpecification(StaffSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always exclude deleted records
            predicates.add(cb.isFalse(root.get("deleted")));

            if (criteria.getStaffType() != null) {
                predicates.add(cb.equal(root.get("staffType"), criteria.getStaffType()));
            }

            if (criteria.getDepartmentId() != null) {
                predicates.add(cb.equal(root.get("departmentId"), criteria.getDepartmentId()));
            }

            if (criteria.getDesignationId() != null) {
                predicates.add(cb.equal(root.get("designationId"), criteria.getDesignationId()));
            }

            if (criteria.getEmploymentType() != null) {
                predicates.add(cb.equal(root.get("employmentType"), criteria.getEmploymentType()));
            }

            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }

            if (criteria.getActive() != null) {
                predicates.add(cb.equal(root.get("isActive"), criteria.getActive()));
            }

            if (criteria.getJoiningDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("joiningDate"), criteria.getJoiningDateFrom()));
            }

            if (criteria.getJoiningDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("joiningDate"), criteria.getJoiningDateTo()));
            }

            if (StringUtils.hasText(criteria.getSearchText())) {
                String pattern = "%" + criteria.getSearchText().toLowerCase() + "%";
                Predicate employeeCodeMatch = cb.like(cb.lower(root.get("employeeCode")), pattern);
                Predicate firstNameMatch = cb.like(cb.lower(root.get("firstName")), pattern);
                Predicate middleNameMatch = cb.like(cb.lower(root.get("middleName")), pattern);
                Predicate lastNameMatch = cb.like(cb.lower(root.get("lastName")), pattern);
                Predicate phoneMatch = cb.like(cb.lower(root.get("phonePrimary")), pattern);
                Predicate emailMatch = cb.like(cb.lower(root.get("email")), pattern);
                Predicate regNumMatch = cb.like(cb.lower(root.get("registrationNumber")), pattern);
                
                // Full name match
                Predicate fullNameMatch = cb.like(cb.lower(cb.concat(cb.concat(root.get("firstName"), " "), root.get("lastName"))), pattern);
                
                predicates.add(cb.or(
                        employeeCodeMatch, firstNameMatch, middleNameMatch, lastNameMatch,
                        phoneMatch, emailMatch, regNumMatch, fullNameMatch
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

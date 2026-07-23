package com.example.HospitaManagmentSystemDemo.service;

import com.example.HospitaManagmentSystemDemo.entity.EmployeeCodeSequence;
import com.example.HospitaManagmentSystemDemo.repository.EmployeeCodeSequenceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeCodeGenerator {

    private final EmployeeCodeSequenceRepository sequenceRepository;
    private final String prefix;

    public EmployeeCodeGenerator(
            EmployeeCodeSequenceRepository sequenceRepository,
            @Value("${hms.staff.employee-code-prefix:HMS-STF}") String prefix) {
        this.sequenceRepository = sequenceRepository;
        this.prefix = prefix;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateCode() {
        EmployeeCodeSequence sequence = sequenceRepository.getSequenceForUpdate()
                .orElseThrow(() -> new IllegalStateException("Employee Code Sequence not initialized in DB"));
        
        Long currentVal = sequence.getNextVal();
        sequence.setNextVal(currentVal + 1);
        sequenceRepository.save(sequence);
        
        return String.format("%s-%06d", prefix, currentVal);
    }
}

package com.sam.customerbatchprocessor.repository;

import com.sam.customerbatchprocessor.entity.FailedRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedRecordRepository extends JpaRepository<FailedRecord, Long> {
}


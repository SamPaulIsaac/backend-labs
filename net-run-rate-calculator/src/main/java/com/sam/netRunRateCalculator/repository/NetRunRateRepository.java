package com.sam.netRunRateCalculator.repository;

import com.sam.netRunRateCalculator.entity.NetRunRate;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NetRunRateRepository extends JpaRepository<NetRunRate, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<NetRunRate> findByTeam_Id(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE NetRunRate n SET n.nrr = :nrr WHERE n.id = :id")
    int updateNetRunRate(@Param("id") Long id, @Param("nrr") Double nrr);

}

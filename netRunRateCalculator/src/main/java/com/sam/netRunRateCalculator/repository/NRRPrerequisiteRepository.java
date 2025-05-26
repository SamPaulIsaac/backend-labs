package com.sam.netRunRateCalculator.repository;

import com.sam.netRunRateCalculator.entity.NRRPrerequisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NRRPrerequisiteRepository extends JpaRepository<NRRPrerequisite, Long> {

    List<NRRPrerequisite> findByTeam_Name(String name);
}

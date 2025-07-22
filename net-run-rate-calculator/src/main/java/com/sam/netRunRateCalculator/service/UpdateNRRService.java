package com.sam.netRunRateCalculator.service;

import com.sam.netRunRateCalculator.entity.NetRunRate;
import com.sam.netRunRateCalculator.entity.Team;
import com.sam.netRunRateCalculator.repository.NetRunRateRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UpdateNRRService {
    private final NetRunRateRepository netRunRateRepository;

    @Transactional
    public void updateNRR(Team team, Double result) {
        netRunRateRepository.findByTeam_Id(team.getId()).ifPresentOrElse(nrr -> netRunRateRepository.updateNetRunRate(team.getId(), result), () -> netRunRateRepository.saveAndFlush(NetRunRate.builder().team(team).nrr(result).build()));
        log.info("Updated NRR for team '{}': {}", team.getName(), result);
    }
}
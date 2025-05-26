package com.sam.netRunRateCalculator.service;

import com.sam.netRunRateCalculator.dto.NRRPrerequisiteDto;
import com.sam.netRunRateCalculator.entity.NRRPrerequisite;
import com.sam.netRunRateCalculator.entity.NetRunRate;
import com.sam.netRunRateCalculator.entity.Team;
import com.sam.netRunRateCalculator.repository.NRRPrerequisiteRepository;
import com.sam.netRunRateCalculator.repository.NetRunRateRepository;
import com.sam.netRunRateCalculator.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class NRRService {
    private TeamRepository teamRepository;
    private NRRPrerequisiteRepository nrrPrerequisiteRepository;
    private NetRunRateRepository netRunRateRepository;
    private NRRCalculatorService nrrCalculatorService;

    @Transactional
    public String saveTeam(String teamName) {
        Team saved = teamRepository.saveAndFlush(Team.builder().name(teamName).build());
        return saved.getName();
    }

    @Transactional
    public void saveNRRPrerequisite(NRRPrerequisiteDto nrrPrerequisiteDto) {
        Team team = teamRepository.findByName(nrrPrerequisiteDto.getTeam())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Team '%s' does not exist.", nrrPrerequisiteDto.getTeam())));

        NRRPrerequisite saved = nrrPrerequisiteRepository.saveAndFlush(
                NRRPrerequisite.builder()
                        .team(team)
                        .matchNumber(nrrPrerequisiteDto.getMatchNumber())
                        .noOfRunsScored(nrrPrerequisiteDto.getNoOfRunsScored())
                        .noOfOversConsumed(nrrPrerequisiteDto.getNoOfOversConsumed())
                        .noOfRunsGiven(nrrPrerequisiteDto.getNoOfRunsGiven())
                        .noOfOversBowled(nrrPrerequisiteDto.getNoOfOversBowled()).build());

        nrrCalculatorService.calculateAndUpdateNRR(saved);
    }

    @Transactional
    public Double getNetRunRateForTeam(String teamName) {
        Team team = teamRepository.findByName(teamName).orElseThrow(() ->
                new EntityNotFoundException(String.format("Team '%s' does not exist.", teamName)));

        NetRunRate netRunRate = netRunRateRepository.findByTeam_Id(team.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("NRR does not exist for the requested team: '%s'.", team.getName())));

        return netRunRate.getNrr();
    }
}

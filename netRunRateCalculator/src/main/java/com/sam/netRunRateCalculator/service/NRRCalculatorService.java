package com.sam.netRunRateCalculator.service;

import com.sam.netRunRateCalculator.entity.NRRPrerequisite;
import com.sam.netRunRateCalculator.entity.Team;
import com.sam.netRunRateCalculator.globalControllerAdvice.BadRequestException;
import com.sam.netRunRateCalculator.repository.NRRPrerequisiteRepository;
import com.sam.netRunRateCalculator.repository.NetRunRateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NRRCalculatorService {
    private final UpdateNRRService updateNRRService;
    private final NRRPrerequisiteRepository nrrPrerequisiteRepository;
    private final NetRunRateRepository netRunRateRepository;

    /*
     * ========================== IMPORTANT ==========================
     * In the event of a team being all out in less than its full quota of overs,
     * the calculation of its net run rate (NRR) shall be based on the full quota
     * of overs to which it would have been entitled, not the actual number
     * of overs faced.
     *
     * This scenario is not handled and will be included
     * in the upcoming release.
     * ===============================================================
     */


    @Async
    public void calculateAndUpdateNRR(NRRPrerequisite nrrPrerequisite) {
        Team team = nrrPrerequisite.getTeam();

        List<NRRPrerequisite> nrrPrerequisiteList = nrrPrerequisiteRepository.findByTeam_Name(team.getName());

        long runsScored = nrrPrerequisiteList.stream().mapToLong(NRRPrerequisite::getNoOfRunsScored).sum();

        long runsGiven = nrrPrerequisiteList.stream().mapToLong(NRRPrerequisite::getNoOfRunsGiven).sum();

        double oversConsumed = nrrPrerequisiteList.stream().map(NRRPrerequisite::getNoOfOversConsumed).mapToDouble(NRRCalculatorService::convertOvers).sum();

        double oversBowled = nrrPrerequisiteList.stream().map(NRRPrerequisite::getNoOfOversBowled).mapToDouble(NRRCalculatorService::convertOvers).sum();

        if (oversConsumed == 0.0 || oversBowled == 0.0)
            throw new BadRequestException(String.format("Overs consumed or bowled is zero for team: '%s'.", team.getName()));

        double result = (runsScored / oversConsumed) - (runsGiven / oversBowled);
        Double roundedNRR = Math.round(result * 1000.0) / 1000.0; // round to 3 decimal places
        updateNRRService.updateNRR(team, roundedNRR);
    }


    private static double convertOvers(double overs) {
        int wholeOvers = (int) overs;
        int balls = (int) Math.round((overs - wholeOvers) * 10); // 0.1 = 1 ball, 0.6 = 6 balls
        if (balls >= 6) {
            wholeOvers += balls / 6;
            balls = balls % 6;
        }
        return wholeOvers + balls / 6.0;
    }
}

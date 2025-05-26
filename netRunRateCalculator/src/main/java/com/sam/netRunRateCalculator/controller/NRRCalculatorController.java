package com.sam.netRunRateCalculator.controller;

import com.sam.netRunRateCalculator.dto.NRRPrerequisiteDto;
import com.sam.netRunRateCalculator.globalControllerAdvice.BadRequestException;
import com.sam.netRunRateCalculator.service.NRRService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nrr")
@AllArgsConstructor
@Slf4j
public class NRRCalculatorController {
    private final NRRService service;

    @PostMapping("/saveTeam")
    public ResponseEntity<String> saveTeam(@RequestParam String teamName) {
        if (teamName == null || teamName.trim().isEmpty())
            throw new BadRequestException("Team name must not be empty.");

        log.info("Request received to save team: {}", teamName);
        String saved = service.saveTeam(teamName);
        return ResponseEntity.ok("Saved the request team: " + saved);
    }

    @PostMapping("/saveNRRPrerequisite")
    public ResponseEntity<String> saveNRRPrerequisite(@RequestBody NRRPrerequisiteDto nrrPrerequisiteDto) {
        log.info("Request received to save nrr prerequisite: {}", nrrPrerequisiteDto);
        service.saveNRRPrerequisite(nrrPrerequisiteDto);
        return ResponseEntity.ok("Saved the nrr prerequisite.");
    }

    @GetMapping("/{team}")
    public ResponseEntity<String> getNetRunRateForTeam(@PathVariable(name = "team") String teamName) {
        log.info("Request received to get nrr for the team: {}", teamName);
        Double nrr = service.getNetRunRateForTeam(teamName);
        return ResponseEntity.ok("Run rate for the team: " + teamName + " is: " + nrr);
    }
}

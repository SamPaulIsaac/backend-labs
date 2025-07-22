package com.sam.netRunRateCalculator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nrr_prerequisite")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NRRPrerequisite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(nullable = false)
    private Long matchNumber;

    @Column(nullable = false)
    private Long noOfRunsScored;

    @Column(nullable = false)
    private Double noOfOversConsumed;

    @Column(nullable = false)
    private Long noOfRunsGiven;

    @Column(nullable = false)
    private Double noOfOversBowled;
}

package com.sam.netRunRateCalculator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "net_run_rate", uniqueConstraints = @UniqueConstraint(columnNames = {"team_id"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NetRunRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", unique = true, nullable = false)
    private Team team;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Team name must not be blank")
    private Double nrr;
}

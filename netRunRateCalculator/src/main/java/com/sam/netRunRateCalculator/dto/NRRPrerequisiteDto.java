package com.sam.netRunRateCalculator.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NRRPrerequisiteDto {

    @NotBlank(message = "Team name must not be blank")
    private String team;

    @NotNull(message = "Match number must not be null")
    @Positive(message = "Match number must be a positive number")
    private Long matchNumber;

    @NotNull(message = "Number of runs scored must not be null")
    @Min(value = 0, message = "Runs scored cannot be negative")
    private Long noOfRunsScored;

    @NotNull(message = "Overs consumed must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Overs consumed must be greater than 0")
    private Double noOfOversConsumed;

    @NotNull(message = "Number of runs given must not be null")
    @Min(value = 0, message = "Runs given cannot be negative")
    private Long noOfRunsGiven;

    @NotNull(message = "Overs bowled must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Overs bowled must be greater than 0")
    private Double noOfOversBowled;
}

package com.sam.alarmApplication.dto;

import com.sam.alarmApplication.utility.RecurrencePattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDto {

    private Long id;
    private String title;
    private LocalDate eventDate;
    private String recipients;
    private RecurrencePattern recurrencePattern;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

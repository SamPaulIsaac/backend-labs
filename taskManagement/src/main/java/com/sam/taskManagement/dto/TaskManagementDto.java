package com.sam.taskManagement.dto;

import com.sam.taskManagement.domain.taskManagement.Priority;
import com.sam.taskManagement.domain.taskManagement.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskManagementDto {

    private String name;
    private String description;
    private TaskStatus status;
    private String comment;
    private LocalDate date;
    private Priority priority;
}

package com.sam.relationshiphub.onetomany.dto.request;

import com.sam.relationshiphub.onetomany.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String description;
    private Status status;
}


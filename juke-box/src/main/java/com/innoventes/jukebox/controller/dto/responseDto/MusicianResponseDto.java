package com.innoventes.jukebox.controller.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicianResponseDto {
    private String name;
    private String musicianType;
}

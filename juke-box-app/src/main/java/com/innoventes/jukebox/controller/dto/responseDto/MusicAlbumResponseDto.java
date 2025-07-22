package com.innoventes.jukebox.controller.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicAlbumResponseDto {
    private Long id;
    private String albumName;
    private Date dateOfRelease;
    private String genre;
    private int price;
    private String description;
    private List<MusicianResponseDto> musicianResponseDtoList;
}

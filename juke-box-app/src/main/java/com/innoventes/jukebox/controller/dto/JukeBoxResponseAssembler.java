package com.innoventes.jukebox.controller.dto;

import com.innoventes.jukebox.controller.dto.responseDto.MusicAlbumResponseDto;
import com.innoventes.jukebox.controller.dto.responseDto.MusicianResponseDto;
import com.innoventes.jukebox.domain.MusicAlbum;
import com.innoventes.jukebox.domain.Musician;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JukeBoxResponseAssembler {

    public MusicAlbumResponseDto toMusicDto(MusicAlbum musicAlbum) {
        return MusicAlbumResponseDto.builder()
                .id(musicAlbum.getId())
                .albumName(musicAlbum.getAlbumName())
                .dateOfRelease(musicAlbum.getDateOfRelease())
                .genre(musicAlbum.getGenre())
                .price(musicAlbum.getPrice())
                .description(musicAlbum.getDescription())
                .musicianResponseDtoList(buildMusicianDto(musicAlbum.getMusicianList()))
                .build();
    }

    public List<MusicianResponseDto> buildMusicianDto(List<Musician> musicianList) {
        return musicianList.stream().map(this::toMusicianDto).collect(Collectors.toList());
    }

    public MusicianResponseDto toMusicianDto(Musician musician) {
        return MusicianResponseDto.builder()
                .name(musician.getName())
                .musicianType(musician.getMusicianType())
                .build();
    }

    public List<MusicAlbumResponseDto> assembleMusicAlbumsDto(List<MusicAlbum> musicAlbumList) {
        return musicAlbumList.stream().map(this::toMusicDto).collect(Collectors.toList());
    }
}

package com.innoventes.jukebox.helper;

import com.innoventes.jukebox.controller.dto.requestDto.MusicAlbumRequestDto;
import com.innoventes.jukebox.controller.dto.requestDto.MusicianRequestDto;
import com.innoventes.jukebox.controller.dto.responseDto.MusicAlbumResponseDto;
import com.innoventes.jukebox.controller.dto.responseDto.MusicianResponseDto;
import com.innoventes.jukebox.domain.MusicAlbum;
import com.innoventes.jukebox.domain.Musician;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JukeBoxHelper {

    public static List<Musician> buildMusicians() {
        List<Musician> musicians = new ArrayList<>();
        Musician experiencedMusician = Musician.builder()
                .name("Matthew")
                .musicianType("Vocalist")
                .musicAlbum(MusicAlbum.builder().albumName("Album 2020").build())
                .build();

        Musician novice = Musician.builder()
                .name("Sam")
                .musicianType("Guitarist")
                .musicAlbum(MusicAlbum.builder().albumName("Explore 2021").build())
                .build();
        musicians.add(experiencedMusician);
        musicians.add(novice);
        return musicians;
    }

    public static List<MusicAlbum> buildMusicAlbums() {
        MusicAlbum allTimeFavourite = MusicAlbum.builder()
                .albumName("album 1")
                .dateOfRelease(Date.valueOf(LocalDate.ofYearDay(2008, 25)))
                .genre("Electronic Dance Music")
                .price(550)
                .description("Fireflies")
                .musicianList(buildMusicians())
                .build();

        MusicAlbum newlyReleased = MusicAlbum.builder()
                .albumName("album 2")
                .dateOfRelease(Date.valueOf(LocalDate.ofYearDay(2028, 50)))
                .genre("Dance Music")
                .price(750)
                .description("Mega Three Stallion")
                .musicianList(buildMusicians())
                .build();
        return Arrays.asList(allTimeFavourite, newlyReleased);
    }

    public static MusicAlbumRequestDto buildMusicAlbumRequestDtoToSave(String albumName, int price, String description) {
        return MusicAlbumRequestDto.builder()
                .albumName(albumName)
                .dateOfRelease(Date.valueOf(LocalDate.ofYearDay(2028, 50)))
                .genre("Dance Music")
                .price(price)
                .description(description)
                .musicianRequestDtoList(Collections.singletonList(
                        MusicianRequestDto.builder()
                                .name("Champ")
                                .musicianType("Guitarist")
                                .build()
                ))
                .build();
    }

    public static MusicAlbumResponseDto buildMusicAlbumResponseDto(String albumName, int price, String description) {
        return MusicAlbumResponseDto.builder()
                .albumName(albumName)
                .dateOfRelease(Date.valueOf(LocalDate.ofYearDay(2028, 50)))
                .price(price)
                .genre("Dance Music")
                .description(description)
                .musicianResponseDtoList(Collections.emptyList())
                .build();
    }

    public static MusicianRequestDto buildMusicianRequestDtoToSave(String name, String type) {
        return MusicianRequestDto.builder()
                .name(name)
                .musicianType(type)
                .build();
    }

    public static MusicianRequestDto buildMusicianRequestDtoAlongWithMusicAlbumToSave(String name, String type) {
        return MusicianRequestDto.builder()
                .name(name)
                .musicianType(type)
                .musicAlbumRequestDto(MusicAlbumRequestDto.builder()
                        .albumName("album 1")
                        .build())
                .build();
    }

    public static MusicianResponseDto buildMusicianResponseDto(String name, String type) {
        return MusicianResponseDto.builder()
                .name(name)
                .musicianType(type)
                .build();
    }

    public static MusicAlbumRequestDto buildMusicAlbumRequestDtoToUpdate(String albumName, int price, String description) {
        return MusicAlbumRequestDto.builder()
                .albumName(albumName)
                .dateOfRelease(Date.valueOf(LocalDate.ofYearDay(2028, 50)))
                .genre("Dance Music")
                .price(price)
                .description(description)
                .musicianRequestDtoList(Collections.singletonList(
                        MusicianRequestDto.builder()
                                .name("Champ")
                                .musicianType("Guitarist")
                                .build()
                ))
                .build();
    }

    public static MusicAlbum updatedMusicAlbums(int price) {
        return MusicAlbum.builder()
                .albumName("album 1")
                .dateOfRelease(Date.valueOf(LocalDate.ofYearDay(2008, 25)))
                .genre("Electronic Dance Music")
                .price(price)
                .description("Mega Three Stallion")
                .musicianList(buildMusicians())
                .build();
    }

    public static MusicianRequestDto buildMusicianRequestDtoToUpdate(String name, String type) {
        return MusicianRequestDto.builder()
                .name(name)
                .musicianType(type)
                .build();
    }

    public static Musician updatedMusician(String name, String type) {
        return Musician.builder()
                .name(name)
                .musicianType(type)
                .build();
    }
}
